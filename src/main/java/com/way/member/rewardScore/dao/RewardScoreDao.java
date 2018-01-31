package com.way.member.rewardScore.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.rewardScore.entity.RewardScoreEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述：积分Dao
 *
 * @Author：xinpei.xu
 * @Date：2017/10/14 10:12
 */
public interface RewardScoreDao extends IBaseMapper {

    /**
     * 增加积分记录
     * @param rewardScoreEntity
     */
    void saveRewardScore(RewardScoreEntity rewardScoreEntity);

    /**
     * 查询总页数
     * @param invitationCode
     * @return
     */
    Integer getRewardScoreDetailCount(String invitationCode);

    /**
     * 分页查询
     * @param invitationCode
     * @param pageNumber
     * @return
     */
    List<RewardScoreEntity> getRewardScoreDetailList(@Param("invitationCode") String invitationCode, @Param("pageNumber") Integer pageNumber);
}
