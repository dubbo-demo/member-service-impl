package com.way.member.friend.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.friend.dto.FriendsInfoDto;
import com.way.member.friend.dto.GroupInfoDto;
import com.way.member.friend.entity.FriendsInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: FriendsInfoDao
 * @Description: 好友信息Dao
 * @author xinpei.xu
 * @date 2017/08/23 19:14
 *
 */
public interface FriendsInfoDao extends IBaseMapper {

    /**
     * 出退出前查看的好友信息
     * @param invitationCode
     * @return
     */
    List<FriendsInfoDto> getFriendsInfoBeforeExit(String invitationCode);

    /**
     * 根据组ID获取好友信息
     * @param phoneNo
     * @param groupId
     * @return
     */
    List<FriendsInfoDto> getRealtimePositionByGroupId(@Param("phoneNo") String phoneNo, @Param("groupId") String groupId);

    /**
     * 更新好友是否退出前查看状态
     * @param invitationCode
     * @param groupId
     * @param state
     */
    void updateIsCheckBeforeExitByGroupId(@Param("invitationCode") String invitationCode, @Param("groupId") String groupId, @Param("state") Integer state);

    /**
     * 取消查看好友实时坐标
     * @param invitationCode
     * @param friendInvitationCodes
     * @param state
     */
    void updateIsCheckBeforeExitByFriendInvitationCodes(@Param("invitationCode") String invitationCode, @Param("friendInvitationCodes") List<String> friendInvitationCodes, @Param("state") Integer state);

    /**
     * 查询好友信息
     * @param invitationCode
     * @param friendInvitationCode
     * @return
     */
    FriendsInfoDto getFriendInfo(@Param("invitationCode") String invitationCode, @Param("friendInvitationCode") String friendInvitationCode);

    /**
     * 查询好友列表
     * @param invitationCode
     * @return
     */
    List<FriendsInfoDto> getFriendList(String invitationCode);

    /**
     * 修改好友信息
     * @param entity
     */
    void modifyFriendInfo(FriendsInfoEntity entity);

    /**
     * 修改被授权人好友信息
     * @param entity
     */
    void modifyAuthorizedFriendInfo(FriendsInfoEntity entity);

    /**
     * 删除好友
     * @param invitationCode
     * @param friendInvitationCode
     */
    void deleteFriend(@Param("invitationCode") String invitationCode, @Param("friendInvitationCode") String friendInvitationCode);

    /**
     * 根据组ID获取好友信息
     * @param groupId
     * @return
     */
    List<FriendsInfoDto> getFriendListByGroupId(String groupId);

    /**
     * 将好友组信息清空
     * @param invitationCode
     * @param groupId
     */
    void updateFriendsGroupInfo(@Param("invitationCode") String invitationCode, @Param("groupId") String groupId);

    /**
     * 将好友添加到分组
     * @param friendInvitationCode
     * @param dto
     */
    void moveFriendToGroup(@Param("friendInvitationCode") String friendInvitationCode, @Param("dto") GroupInfoDto dto);

    /**
     * 将好友从分组中移除
     * @param invitationCode
     * @param friendInvitationCode
     */
    void removeFriendFromGroup(@Param("invitationCode") String invitationCode, @Param("friendInvitationCode") String friendInvitationCode);

    /**
     * 添加好友
     * @param entity
     */
    void addFriendInfo(FriendsInfoEntity entity);

    /**
     * 查询是否被好友授权可见
     * @param invitationCode
     * @param friendInvitationCode
     * @return
     */
    FriendsInfoDto checkIsAuthorizedVisible(@Param("invitationCode") String invitationCode, @Param("friendInvitationCode") String friendInvitationCode);
}
