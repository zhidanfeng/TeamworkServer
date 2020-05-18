package com.teamwork.service.impl;

import com.teamwork.dao.ProjectTemplateDao;
import com.teamwork.entity.T_PROJECT_TEMPLATE;
import com.teamwork.service.ProjectTemplateService;
import com.teamwork.utils.DateUtil;
import com.teamwork.utils.GuidUtils;
import com.teamwork.utils.RedisUtil;
import com.teamwork.vo.ProjectTemplateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectTemplateServiceImpl implements ProjectTemplateService {

    @Autowired
    private ProjectTemplateDao projectTemplateDao;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<ProjectTemplateVO> getAll() {
        List<T_PROJECT_TEMPLATE> templateList = this.projectTemplateDao.selectList(null);
        List<ProjectTemplateVO> voList = new ArrayList<>();
        for (T_PROJECT_TEMPLATE entity : templateList){
            ProjectTemplateVO vo = new ProjectTemplateVO();
            BeanUtils.copyProperties(entity,vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public String add(ProjectTemplateVO vo) {
        T_PROJECT_TEMPLATE entity = new T_PROJECT_TEMPLATE();
        BeanUtils.copyProperties(vo, entity);
        entity.setTemplateId(GuidUtils.getUUID());
        entity.setCreateTime(DateUtil.getCurrentDate());
        entity.setUpdateTime(entity.getCreateTime());
        int rows = this.projectTemplateDao.insert(entity);
        return rows > 0 ? entity.getTemplateId() : "";
    }
}
