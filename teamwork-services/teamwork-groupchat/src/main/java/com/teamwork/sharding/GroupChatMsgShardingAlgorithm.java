package com.teamwork.sharding;


import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * 聊天分表
 */
public class GroupChatMsgShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> collection, Collection<ShardingValue> collection1) {
        Collection<String> routTables = new HashSet<String>();
        ArrayList<ShardingValue> shardingValues = (ArrayList) collection1;
        for (ShardingValue shardingValue : shardingValues) {
            if (!"msg_time".equals(shardingValue.getColumnName()))
                continue;
            if (shardingValue instanceof RangeShardingValue) { //查询
                RangeShardingValue msgTime = (RangeShardingValue) shardingValue;
                Range<Comparable> msgTimeList = msgTime.getValueRange();
                if (msgTimeList != null) {
                    Comparable lowerEnd = msgTimeList.lowerEndpoint();
                    Comparable upperEnd = msgTimeList.upperEndpoint();
                    if (lowerEnd instanceof Long) {
                        Long startTime = (Long) lowerEnd;
                        String s = DateUtil.format(DateUtil.date(startTime), "yyyyMMdd");
                        routTables.add(shardingValue.getLogicTableName() + s);
                    }
                    if (upperEnd instanceof Long) {
                        Long endTime = (Long) upperEnd;
                        String s = DateUtil.format(DateUtil.date(endTime), "yyyyMMdd");
                        routTables.add(shardingValue.getLogicTableName() + s);
                    }
                }
            } else if (shardingValue instanceof ListShardingValue) { //插入
                ListShardingValue listShardingValue = (ListShardingValue) shardingValue;
                LinkedList<Long> linkedList = (LinkedList<Long>) listShardingValue.getValues();
                long msgTime = linkedList.get(0);
                String s = DateUtil.format(DateUtil.date(msgTime), "yyyyMMdd");
                routTables.add(shardingValue.getLogicTableName() + s);
            }
        }
        return routTables;
    }
}
