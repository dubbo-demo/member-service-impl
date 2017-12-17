package com.way.member.withdrawal.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.withdrawal.dto.WithdrawalInfoDto;
import com.way.member.withdrawal.entity.WithdrawalInfoEntity;

import java.util.List;

/**
 * 功能描述：提现信息Dao
 *
 * @Author：xinpei.xu
 */
public interface WithdrawalInfoDao extends IBaseMapper {

    /**
     * 增加积分提现记录
     * @param withdrawalInfoDto
     */
    void withdrawalRewardScore(WithdrawalInfoDto withdrawalInfoDto);

    /**
     * 获取积分提现记录
     * @param phoneNo
     * @return
     */
    List<WithdrawalInfoEntity> getWithdrawalRewardScoreInfo(String phoneNo);
}
