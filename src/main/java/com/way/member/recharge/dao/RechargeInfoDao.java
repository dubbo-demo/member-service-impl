package com.way.member.recharge.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.recharge.dto.RechargeInfoDto;
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
     * @param invitationCode
     * @return
     */
    Integer getRechargeInfoCount(String invitationCode);

    /**
     * 分页查询
     * @param invitationCode
     * @param pageNumber
     * @return
     */
    List<RechargeInfoEntity> getRechargeInfoList(@Param("invitationCode") String invitationCode, @Param("pageNumber") int pageNumber);

    /**
     * 更新充值记录表
     * @param dto
     */
    void addRechargeInfoDto(RechargeInfoDto dto);
}
