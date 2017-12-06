package com.way.member.rewardScore.service;

import com.way.common.util.CommonUtils;
import com.way.member.rewardScore.dao.RewardScoreDao;
import com.way.member.rewardScore.dto.RewardScoreDto;
import com.way.member.rewardScore.entity.RewardScoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述：积分ServiceImpl
 *
 * @Author：xinpei.xu
 * @Date：2017/10/14 10:10
 */
@Service
public class RewardScoreServiceImpl implements RewardScoreService {

    @Autowired
    private RewardScoreDao rewardScoreDao;

    /**
     * 增加积分记录
     * @param rewardScoreDto
     */
    @Override
    public void saveRewardScore(RewardScoreDto rewardScoreDto) {
        RewardScoreEntity rewardScoreEntity = CommonUtils.transform(rewardScoreDto, RewardScoreEntity.class);
        rewardScoreDao.saveRewardScore(rewardScoreEntity);
    }
}
