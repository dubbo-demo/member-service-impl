package com.way.member.withdrawal.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.withdrawal.dto.WithdrawalInfoDto;
import com.way.member.withdrawal.entity.WithdrawalInfoEntity;
import org.apache.ibatis.annotations.Param;

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
     * 查询总页数
     * @param invitationCode
     * @return
     */
    Integer getWithdrawalRewardScoreCount(String invitationCode);

    /**
     * 获取积分提现记录
     * @param invitationCode
     * @param pageNumber
     * @return
     */
    List<WithdrawalInfoEntity> getWithdrawalRewardScoreInfo(@Param("invitationCode") String invitationCode, @Param("pageNumber") int pageNumber);
}
