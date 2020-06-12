package com.teamwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teamwork.dao.TaskAttachmentDao;
import com.teamwork.dao.TaskOpRecordDao;
import com.teamwork.entity.T_TASK_ATTACHMENT;
import com.teamwork.entity.T_TASK_OP_RECORD;
import com.teamwork.service.CommonService;
import com.teamwork.service.TaskOpRecordService;
import com.teamwork.vo.TaskAttachmentVO;
import com.teamwork.vo.TaskOpRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
public class TaskOpRecordServiceImpl implements TaskOpRecordService {

    @Autowired
    private TaskOpRecordDao taskOpRecordDao;
    @Autowired
    private TaskAttachmentDao taskAttachmentDao;
    @Autowired
    private CommonService commonService;
    @Resource
    private SnowflakeIdWorker idWorker;

    @Override
    public List<TaskOpRecordVO> getListByTaskId(long taskId) {
        QueryWrapper queryWrapper = new QueryWrapper<T_TASK_OP_RECORD>().eq("task_id",  taskId);
        queryWrapper.orderByAsc("create_time");
        List<T_TASK_OP_RECORD> entityList = taskOpRecordDao.selectList(queryWrapper);
        List<TaskOpRecordVO> list = new ArrayList<>();

        for (T_TASK_OP_RECORD t_task_op_record : entityList) {
            TaskOpRecordVO vo = BeanUtil.copy(t_task_op_record, TaskOpRecordVO.class);

            //关联附件
            if(vo.getRecordType() == 98) {
                List<T_TASK_ATTACHMENT> attachmentList = this.taskAttachmentDao.selectList(new QueryWrapper<T_TASK_ATTACHMENT>().eq("op_record_id", vo.getRecordId()));

                if(attachmentList != null) {
                    if(vo.getAttachmentList() == null) {
                        vo.setAttachmentList(new ArrayList<TaskAttachmentVO>());
                    }
                    attachmentList.forEach((item) -> vo.getAttachmentList().add(BeanUtil.copy(item, TaskAttachmentVO.class)));
                }
            }

            list.add(vo);
        }
        return list;
    }

    @Override
    public boolean addRecord(TaskOpRecordVO record) {
        if(StringUtil.isNullOrEmpty(record.getViewId()) || this.commonService.existViewId(record.getViewId()))
            return false;

        T_TASK_OP_RECORD entity = BeanUtil.copy(record, T_TASK_OP_RECORD.class);
        entity.setRecordId(idWorker.nextId());
        entity.setCreateTime(DateUtil.getCurrentDate());

        //如果动态是附件类型，则获取相关附件
        if(record.getRecordType() == 98) {
            List<TaskAttachmentVO> attachmentList = this.getTempAttachmentList(record.getTaskId());
            attachmentList.forEach((attachmentVO -> {
                T_TASK_ATTACHMENT attachment = BeanUtil.copy(attachmentVO, T_TASK_ATTACHMENT.class);
                attachment.setOpRecordId(entity.getRecordId());
                int rows = this.taskAttachmentDao.insert(attachment);
                //附件入库成功后，删除附件上传缓存记录
                if(rows > 0) {
                    this.taskAttachmentMap.remove(attachment.getAttachmentId());
                }
            }));
        }

        int rows = taskOpRecordDao.insert(entity);
        if(rows > 0) {
            this.taskAttachmentTemp.remove(record.getTaskId());
        }
        return rows > 0;
    }

    @Override
    public boolean addAttachment(TaskAttachmentVO attachment) {
        T_TASK_ATTACHMENT entity = BeanUtil.copy(attachment, T_TASK_ATTACHMENT.class);
        int rows = this.taskAttachmentDao.insert(entity);
        return rows > 0;
    }


    /**
     * 任务-附件上传记录列表 缓存
     */
    private HashMap<String, List<String>> taskAttachmentTemp = new HashMap<>();
    /**
     * 附件信息
     */
    private HashMap<String, TaskAttachmentVO> taskAttachmentMap = new HashMap<>();

    @Override
    public TaskAttachmentVO addAttachment(HttpServletRequest request) throws UnsupportedEncodingException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        List<String> attachmentIdList = new ArrayList<>();
        TaskAttachmentVO attachmentVO = new TaskAttachmentVO();

        if(resolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();

            String taskId = multiRequest.getParameter("taskId");

            String localPath = "C://upload";
            File path = new File(localPath);
            if(!path.exists()) {
                path.mkdir();
            }
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String filepath = localPath + File.separator + new String(file.getOriginalFilename().getBytes(), "UTF-8");
                    // 上传
                    try {
                        file.transferTo(new File(filepath));
                        String attachmentId = GuidUtils.getUUID();

                        if(taskAttachmentTemp.containsKey(taskId)) {
                            List<String> idList = taskAttachmentTemp.get(taskId);
                            idList.add(attachmentId);
                        } else {
                            List<String> idList = new ArrayList<>();
                            idList.add(attachmentId);
                            taskAttachmentTemp.put(taskId, idList);
                        }

                        attachmentVO.setAttachmentId(attachmentId);
                        attachmentVO.setFileName(file.getOriginalFilename());
                        attachmentVO.setFileSize(file.getSize());
                        attachmentVO.setTaskId(Long.parseLong(taskId));
                        taskAttachmentMap.put(attachmentId, attachmentVO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return attachmentVO;
    }

    @Override
    public List<TaskAttachmentVO> getTempAttachmentList(long taskId) {
        List<TaskAttachmentVO> tempAttachmentList = new ArrayList<>();
        List<String> attachmentIdList = this.taskAttachmentTemp.get(taskId);
        if(attachmentIdList != null) {
            attachmentIdList.forEach((id) -> {
                TaskAttachmentVO attachmentInfo = this.taskAttachmentMap.get(id);
                if(attachmentInfo != null) {
                    tempAttachmentList.add(attachmentInfo);
                }
            });
        }
        return tempAttachmentList;
    }
}
