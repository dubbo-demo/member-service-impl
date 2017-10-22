package com.way.member.friend.dao;

import com.way.common.result.ServiceResult;
import com.way.common.rom.IBaseMapper;
import com.way.member.friend.dto.FriendsInfoDto;
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
     * @param phoneNo
     * @return
     */
    List<FriendsInfoDto> getFriendsInfoBeforeExit(String phoneNo);

    /**
     * 根据组ID获取好友信息
     * @param phoneNo
     * @param groupId
     * @return
     */
    List<FriendsInfoDto> getRealtimePositionByGroupId(@Param("phoneNo") String phoneNo, @Param("groupId") String groupId);

    /**
     * 更新好友是否退出前查看状态
     * @param phoneNo
     * @param groupId
     * @param state
     */
    void updateIsCheckBeforeExitByGroupId(@Param("phoneNo") String phoneNo, @Param("groupId") String groupId, @Param("state") Integer state);

    /**
     * 取消查看好友实时坐标
     * @param phoneNo
     * @param friendPhoneNo
     * @param state
     */
    void updateIsCheckBeforeExitByFriendPhoneNo(@Param("phoneNo") String phoneNo, @Param("friendPhoneNo") String friendPhoneNo, @Param("state") Integer state);

    /**
     * 查询好友信息
     * @param phoneNo
     * @param friendPhoneNo
     * @return
     */
    ServiceResult<FriendsInfoDto> getFriendInfo(@Param("phoneNo") String phoneNo, @Param("friendPhoneNo") String friendPhoneNo);

    /**
     * 查询好友列表
     * @param phoneNo
     * @return
     */
    List<FriendsInfoDto> getFriendList(String phoneNo);

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
     * @param phoneNo
     * @param friendPhoneNo
     */
    void deleteFriend(@Param("phoneNo") String phoneNo, String friendPhoneNo);

    /**
     * 根据组ID获取好友信息
     * @param groupId
     * @return
     */
    List<FriendsInfoDto> getFriendListByGroupId(String groupId);

    /**
     * 将好友组信息清空
     * @param phoneNo
     * @param groupId
     */
    void updateFriendsGroupInfo(@Param("phoneNo") String phoneNo, @Param("groupId") String groupId);
}
