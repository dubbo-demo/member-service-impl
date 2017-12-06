package com.way.member.rewardScore.dao;

import com.way.member.rewardScore.entity.RewardScoreEntity;

/**
 * 功能描述：积分Dao
 *
 * @Author：xinpei.xu
 * @Date：2017/10/14 10:12
 */
public interface RewardScoreDao {

    /**
     * 增加积分记录
     * @param rewardScoreEntity
     */
    void saveRewardScore(RewardScoreEntity rewardScoreEntity);
}
