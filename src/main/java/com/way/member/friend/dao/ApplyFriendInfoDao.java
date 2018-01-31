package com.way.member.friend.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.friend.dto.FriendsInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ApplyFriendInfoDao
 * @Description: 申请好友信息Dao
 * @author xinpei.xu
 * @date 2017/09/06 20:21
 *
 */
public interface ApplyFriendInfoDao extends IBaseMapper {

    /**
     * 查询被申请人是否有被申请记录
     * @param invitationCode
     * @param friendInvitationCode
     */
    int getAddFriendInfo(@Param("invitationCode") String invitationCode, @Param("friendInvitationCode") String friendInvitationCode);

    /**
     * 增加被申请记录
     * @param invitationCode
     * @param friendInvitationCode
     * @param applyInfo
     * @param createTime
     * @param modifyTime
     */
    void addApplyFriendInfo(@Param("invitationCode") String invitationCode, @Param("friendInvitationCode") String friendInvitationCode, @Param("applyInfo") String applyInfo, @Param("createTime") Date createTime, @Param("modifyTime") Date modifyTime);

    /**
     * 更新被申请记录
     * @param invitationCode
     * @param friendInvitationCode
     * @param applyInfo
     * @param modifyTime
     */
    void updateApplyFriendInfo(@Param("invitationCode") String invitationCode, @Param("friendInvitationCode") String friendInvitationCode, @Param("applyInfo") String applyInfo, @Param("modifyTime") Date modifyTime);

    /**
     * 获取被申请好友记录
     * @param invitationCode
     * @return
     */
    List<FriendsInfoDto> getApplicationRecordOfFriend(String invitationCode);


    /**
     * 同意/拒绝添加好友申请
     * @param applicationId
     * @param isApprove
     * @return
     */
    void agreeToAddFriend(@Param("applicationId") String applicationId, @Param("isApprove") String isApprove);

}
