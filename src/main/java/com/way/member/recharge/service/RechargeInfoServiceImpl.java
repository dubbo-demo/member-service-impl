package com.way.member.recharge.service;

import com.way.member.recharge.dao.RechargeInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述：充值ServiceImpl
 *
 * @Author：xinpei.xu
 */
@Service
public class RechargeInfoServiceImpl implements RechargeInfoService {

    @Autowired
    private RechargeInfoDao rechargeInfoDao;
}
