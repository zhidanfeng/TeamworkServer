package com.teamwork.service.impl;

import cn.hutool.core.util.StrUtil;
import com.teamwork.service.CommonService;
import com.teamwork.utils.GuidUtils;
import com.teamwork.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    private static final String VIEWID = "viewid_";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getViewId(String viewId) {
        if (StrUtil.isEmpty(viewId)) {
            String key = GuidUtils.getUUID();
            boolean isadd = redisUtil.set(VIEWID + key, key);
            return isadd ? key : "";
        } else {
            Object value = redisUtil.get(VIEWID + viewId);
            if(value == null)
                redisUtil.set(VIEWID + viewId, viewId, 3);
            return value != null ? viewId : "";
        }
    }

    @Override
    public void deleteViewId(String viewId) {
        this.redisUtil.delete(VIEWID + viewId);
    }
}
