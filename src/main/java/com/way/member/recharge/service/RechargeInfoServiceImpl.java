package com.way.member.recharge.service;

import com.way.common.util.CommonUtils;
import com.way.member.recharge.dao.RechargeInfoDao;
import com.way.member.recharge.dto.RechargeInfoDto;
import com.way.member.recharge.entity.RechargeInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述：充值ServiceImpl
 *
 * @Author：xinpei.xu
 */
@Service
public class RechargeInfoServiceImpl implements RechargeInfoService {

    @Autowired
    private RechargeInfoDao rechargeInfoDao;

    /**
     * 查询总页数
     * @param phoneNo
     * @return
     */
    @Override
    public Integer getRechargeInfoCount(String phoneNo) {
        return rechargeInfoDao.getRechargeInfoCount(phoneNo);
    }

    /**
     * 分页查询
     * @param phoneNo
     * @param pageNumber
     * @return
     */
    @Override
    public List<RechargeInfoDto> getRechargeInfoList(String phoneNo, int pageNumber) {
        List<RechargeInfoEntity> list = rechargeInfoDao.getRechargeInfoList(phoneNo, pageNumber);
        return CommonUtils.transformList(list, RechargeInfoDto.class);
    }
}
