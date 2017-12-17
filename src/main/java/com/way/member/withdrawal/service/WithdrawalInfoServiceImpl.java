package com.way.member.withdrawal.service;

import com.way.common.util.CommonUtils;
import com.way.member.withdrawal.dao.WithdrawalInfoDao;
import com.way.member.withdrawal.dto.WithdrawalInfoDto;
import com.way.member.withdrawal.entity.WithdrawalInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述：提现信息ServiceImpl
 *
 * @Author：xinpei.xu
 */
@Service
public class WithdrawalInfoServiceImpl implements WithdrawalInfoService {

    @Autowired
    private WithdrawalInfoDao withdrawalInfoDao;

    /**
     * 增加积分提现记录
     * @param withdrawalInfoDto
     */
    @Override
    public void withdrawalRewardScore(WithdrawalInfoDto withdrawalInfoDto) {
        withdrawalInfoDao.withdrawalRewardScore(withdrawalInfoDto);
    }

    /**
     * 获取积分提现记录
     * @param phoneNo
     * @return
     */
    @Override
    public List<WithdrawalInfoDto> getWithdrawalRewardScoreInfo(String phoneNo) {
        List<WithdrawalInfoEntity> entity = withdrawalInfoDao.getWithdrawalRewardScoreInfo(phoneNo);
        return CommonUtils.transformList(entity, WithdrawalInfoDto.class);
    }

}
