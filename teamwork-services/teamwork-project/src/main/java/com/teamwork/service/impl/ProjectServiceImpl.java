package com.teamwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teamwork.dao.ProjectDao;
import com.teamwork.dao.ProjectUserLinkDao;
import com.teamwork.entity.LINK_PROJECT_USER;
import com.teamwork.entity.T_PROJECT;
import com.teamwork.service.ProjectService;
import com.teamwork.utils.DateUtil;
import com.teamwork.utils.GuidUtils;
import com.teamwork.utils.RedisUtil;
import com.teamwork.utils.SnowflakeIdWorker;
import com.teamwork.vo.ProjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;
//    @Autowired
//    private TaskGroupTemplateDao taskGroupTemplateDao;
//    @Autowired
//    private TaskGroupDao taskGroupDao;
    @Autowired
    private ProjectUserLinkDao projectUserLinkDao;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private SnowflakeIdWorker idWorker;

    @Override
    public List<ProjectVO> getAll() {
        List<ProjectVO> vos = new ArrayList<>();
        QueryWrapper<T_PROJECT> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("create_time");
        List<T_PROJECT> entityList = this.projectDao.selectList(queryWrapper);
        for (T_PROJECT project : entityList) {
            ProjectVO vo = new ProjectVO();
            BeanUtils.copyProperties(project, vo);
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 新建项目
     */
    @Override
    //@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, timeout = -1)
    public String saveProject(ProjectVO vo) {
        //region 保存项目基本信息
        T_PROJECT projectEntity = new T_PROJECT();
        BeanUtils.copyProperties(vo, projectEntity);
        projectEntity.setProjectId(GuidUtils.getUUID());
        projectEntity.setCreateTime(DateUtil.getCurrentDate());
        projectEntity.setUpdateTime(projectEntity.getCreateTime());
        int rows = this.projectDao.insert(projectEntity);
        //endregion

        //region 根据项目模板ID，获取模板中的默认任务分组
//        QueryWrapper<T_TASKGROUP_TEMPLATE> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("template_id", vo.getTemplateId());
//        List<T_TASKGROUP_TEMPLATE> taskTemplateList = taskGroupTemplateDao.selectList(queryWrapper);
//        //endregion
//
//        //region 将获取到的默认任务分组写入该项目的分组表中
//        for (T_TASKGROUP_TEMPLATE taskTemplate : taskTemplateList) {
//            T_TASK_GROUP taskGroup = new T_TASK_GROUP();
//            BeanUtils.copyProperties(taskTemplate, taskGroup);
//            taskGroup.setId(idWorker.nextId());
//            taskGroup.setProjectId(projectEntity.getProjectId());
//            taskGroup.setCreateTime(DateUtil.getCurrentDate());
//            taskGroup.setUpdateTime(taskGroup.getCreateTime());
//            this.taskGroupDao.insert(taskGroup);
//        }
        //endregion

        return rows > 0 ? projectEntity.getProjectId() : "";
    }

    @Override
    public List<String> getProjectByUserId(String userId) {
        //TODO 需要注意，根据最左匹配原则，命中唯一索引
        List<LINK_PROJECT_USER> list = this.projectUserLinkDao.selectList(new QueryWrapper<LINK_PROJECT_USER>().eq("user_id", userId));
        List<String> idList = new ArrayList<>();
        list.forEach((item) -> {
            idList.add(item.getProjectId());
        });
        return idList;
    }
}
