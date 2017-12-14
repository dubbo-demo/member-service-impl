package com.way.member.order.service;

import com.way.common.util.CommonUtils;
import com.way.member.order.dao.MemberOrderInfoDao;
import com.way.member.order.dto.MemberOrderInfoDto;
import com.way.member.order.entity.MemberOrderInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述：用户订单信息ServiceImpl
 *
 * @Author：xinpei.xu
 */
@Service
public class MemberOrderInfoServiceImpl implements MemberOrderInfoService {

    @Autowired
    private MemberOrderInfoDao memberOrderInfoDao;

    /**
     * 保存订单信息
     * @param memberOrderInfoDto
     */
    @Override
    public void saveMemberOrderInfo(MemberOrderInfoDto memberOrderInfoDto) {
        MemberOrderInfoEntity memberOrderInfoEntity = CommonUtils.transform(memberOrderInfoDto, MemberOrderInfoEntity.class);
        memberOrderInfoDao.saveMemberOrderInfo(memberOrderInfoEntity);
    }
}
