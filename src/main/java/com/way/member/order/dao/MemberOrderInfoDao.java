package com.way.member.order.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.order.entity.MemberOrderInfoEntity;

/**
 * 功能描述：
 *
 * @Author：xinpei.xu
 */
public interface MemberOrderInfoDao extends IBaseMapper {

    /**
     * 保存订单信息
     * @param memberOrderInfoEntity
     */
    void saveMemberOrderInfo(MemberOrderInfoEntity memberOrderInfoEntity);
}
