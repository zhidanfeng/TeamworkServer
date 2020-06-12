package com.teamwork.service.impl;

import com.teamwork.service.CommonService;
import com.teamwork.utils.GuidUtils;
import com.teamwork.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getViewId() {
        String key = GuidUtils.getUUID();
        boolean isadd = redisUtil.set("viewid_" + key, key);
        return isadd ? key : "";
    }

    @Override
    public boolean existViewId(String viewId) {
        Object value = redisUtil.get("viewid_" + viewId);
        if(value == null)
            redisUtil.set("viewid_" + viewId, viewId, 3);
        return value != null;
    }

    @Override
    public void deleteViewId(String viewId) {
        this.redisUtil.delete("viewid_" + viewId);
    }
}
