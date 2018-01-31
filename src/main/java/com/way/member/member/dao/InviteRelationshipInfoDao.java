package com.way.member.member.dao;

import com.way.member.member.entity.InviteRelationshipInfoEntity;

/**
 * @ClassName: InviteRelationshipInfoDao
 * @Description: 邀请码关系信息Dao
 * @author: xinpei.xu
 *
 */
public interface InviteRelationshipInfoDao {

    /**
     * 根据邀请码查出邀请人上级用户邀请码
     * @param invitationCode
     * @return
     */
    InviteRelationshipInfoEntity queryInviteRelationshipInfoByUnderNextLevelInvitationCode(String invitationCode);

    /**
     * 保存推荐人层级关系
     * @param inviteRelationshipInfoEntity
     */
    void addInviteRelationshipInfo(InviteRelationshipInfoEntity inviteRelationshipInfoEntity);
}
