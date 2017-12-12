package com.way.member.recharge.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.recharge.entity.RechargeInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述：充值Dao
 *
 * @Author：xinpei.xu
 */
public interface RechargeInfoDao extends IBaseMapper {

    /**
     * 查询总页数
     * @param phoneNo
     * @return
     */
    Integer getRechargeInfoCount(String phoneNo);

    /**
     * 分页查询
     * @param phoneNo
     * @param pageNumber
     * @return
     */
    List<RechargeInfoEntity> getRechargeInfoList(@Param("phoneNo") String phoneNo, @Param("pageNumber") int pageNumber);
}
