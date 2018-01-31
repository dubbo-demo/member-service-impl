package com.way.member.member.service;

import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.member.member.dao.InviteRelationshipInfoDao;
import com.way.member.member.dto.InviteRelationshipInfoDto;
import com.way.member.member.entity.InviteRelationshipInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: InviteRelationshipInfoServiceImpl
 * @Description: 邀请码关系信息ServiceImpl
 * @author: xinpei.xu
 *
 */
@Service
public class InviteRelationshipInfoServiceImpl implements InviteRelationshipInfoService {

    @Autowired
    private InviteRelationshipInfoDao inviteRelationshipInfoDao;

    /**
     * 根据邀请码查出邀请人上级用户邀请码
     * @param invitationCode
     * @return
     */
    @Override
    public ServiceResult<InviteRelationshipInfoDto> queryInviteRelationshipInfoByUnderNextLevelInvitationCode(String invitationCode) {
        ServiceResult<InviteRelationshipInfoDto> serviceResult = ServiceResult.newSuccess();
        // 根据邀请码查出邀请人上级用户邀请码
        InviteRelationshipInfoEntity inviteRelationshipInfoEntity = inviteRelationshipInfoDao.queryInviteRelationshipInfoByUnderNextLevelInvitationCode(invitationCode);
        serviceResult.setData(CommonUtils.transform(inviteRelationshipInfoEntity, InviteRelationshipInfoDto.class));
        return serviceResult;
    }

    /**
     * 保存推荐人层级关系
     * @param inviteRelationshipInfoDto
     */
    @Override
    public void addInviteRelationshipInfo(InviteRelationshipInfoDto inviteRelationshipInfoDto) {
        InviteRelationshipInfoEntity inviteRelationshipInfoEntity = CommonUtils.transform(inviteRelationshipInfoDto, InviteRelationshipInfoEntity.class);
        inviteRelationshipInfoDao.addInviteRelationshipInfo(inviteRelationshipInfoEntity);
    }

    /**
     * 查询用户下级用户数
     * @param invitationCode
     * @return
     */
    @Override
    public Integer getNextLevelCount(String invitationCode) {
        return inviteRelationshipInfoDao.getNextLevelCount(invitationCode);
    }

    /**
     * 查询用户下下级用户数
     * @param invitationCode
     * @return
     */
    @Override
    public Integer getUnderNextLevelCount(String invitationCode) {
        return inviteRelationshipInfoDao.getUnderNextLevelCount(invitationCode);
    }
}
