package com.way.member.friend.service;

import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.member.friend.dao.FriendsInfoDao;
import com.way.member.friend.dto.FriendsInfoDto;
import com.way.member.friend.dto.GroupInfoDto;
import com.way.member.friend.entity.FriendsInfoEntity;
import com.way.member.member.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: FriendsInfoServiceImpl
 * @Description: 好友信息ServiceImp
 * @author xinpei.xu
 * @date 2017/08/23 19:14
 *
 */
@Service
public class FriendsInfoServiceImpl implements FriendsInfoService {

    @Autowired
    private FriendsInfoDao friendsInfoDao;

    /**
     * 出退出前查看的好友信息
     * @param phoneNo
     * @return
     */
    @Override
    public List<FriendsInfoDto> getFriendsInfoBeforeExit(String phoneNo) {
        return friendsInfoDao.getFriendsInfoBeforeExit(phoneNo);
    }

    /**
     * 根据组ID获取好友信息
     * @param phoneNo
     * @param groupId
     * @return
     */
    @Override
    public List<FriendsInfoDto> getRealtimePositionByGroupId(String phoneNo, String groupId) {
        return friendsInfoDao.getRealtimePositionByGroupId(phoneNo, groupId);
    }

    /**
     * 更新好友是否退出前查看状态
     * @param phoneNo
     * @param groupId
     * @param state
     */
    @Override
    public void updateIsCheckBeforeExitByGroupId(String phoneNo, String groupId, Integer state) {
        friendsInfoDao.updateIsCheckBeforeExitByGroupId(phoneNo, groupId, state);
    }

    /**
     * 取消查看好友实时坐标
     * @param phoneNo
     * @param friendPhoneNo
     * @param state
     */
    @Override
    public void updateIsCheckBeforeExitByFriendPhoneNo(String phoneNo, String friendPhoneNo, Integer state) {
        friendsInfoDao.updateIsCheckBeforeExitByFriendPhoneNo(phoneNo, friendPhoneNo, state);
    }

    /**
     * 查询好友信息
     * @param phoneNo
     * @param friendPhoneNo
     * @return
     */
    @Override
    public ServiceResult<FriendsInfoDto> getFriendInfo(String phoneNo, String friendPhoneNo) {
        return friendsInfoDao.getFriendInfo(phoneNo, friendPhoneNo);
    }

    /**
     * 查询好友列表
     * @param phoneNo
     * @return
     */
    @Override
    public List<FriendsInfoDto> getFriendList(String phoneNo) {
        return friendsInfoDao.getFriendList(phoneNo);
    }

    /**
     * 修改好友信息
     * @param phoneNo
     * @param dto
     */
    @Override
    public void modifyFriendInfo(String phoneNo, FriendsInfoDto dto) {
        FriendsInfoEntity entity = new FriendsInfoEntity();
        entity.setPhoneNo(phoneNo);
        entity.setFriendPhoneNo(dto.getFriendPhoneNo());
        entity.setIsAccreditVisible(dto.getIsAccreditVisible());
        entity.setAccreditStartTime(dto.getAccreditStartTime());
        entity.setAccreditEndTime(dto.getAccreditEndTime());
        friendsInfoDao.modifyFriendInfo(entity);
    }

    /**
     * 修改被授权人好友信息
     * @param phoneNo
     * @param dto
     */
    @Override
    public void modifyAuthorizedFriendInfo(String phoneNo, FriendsInfoDto dto) {
        FriendsInfoEntity entity = new FriendsInfoEntity();
        entity.setPhoneNo(dto.getFriendPhoneNo());
        entity.setFriendPhoneNo(phoneNo);
        entity.setIsAuthorizedVisible(dto.getIsAccreditVisible());
        entity.setAuthorizedAccreditStartTime(dto.getAccreditStartTime());
        entity.setAuthorizedAccreditEndTime(dto.getAccreditEndTime());
        friendsInfoDao.modifyAuthorizedFriendInfo(entity);
    }

    /**
     * 删除好友
     * @param phoneNo
     * @param friendPhoneNo
     */
    @Override
    public void deleteFriend(String phoneNo, String friendPhoneNo) {
        friendsInfoDao.deleteFriend(phoneNo, friendPhoneNo);
    }

    /**
     * 根据组ID获取好友信息
     * @param groupId
     * @return
     */
    @Override
    public List<FriendsInfoDto> getFriendListByGroupId(String groupId) {
        return friendsInfoDao.getFriendListByGroupId(groupId);
    }

    /**
     * 将好友组信息清空
     * @param phoneNo
     * @param groupId
     */
    @Override
    public void updateFriendsGroupInfo(String phoneNo, String groupId) {
        friendsInfoDao.updateFriendsGroupInfo(phoneNo, groupId);
    }

    /**
     * 将好友添加到分组
     * @param friendPhoneNo
     * @param groupInfoDto
     */
    @Override
    public void moveFriendToGroup(String friendPhoneNo, GroupInfoDto groupInfoDto) {
        friendsInfoDao.moveFriendToGroup(friendPhoneNo, groupInfoDto);
    }

    /**
     * 将好友从分组中移除
     * @param phoneNo
     * @param friendPhoneNo
     * @return
     */
    @Override
    public ServiceResult<Object> removeFriendFromGroup(String phoneNo, String friendPhoneNo) {
        friendsInfoDao.removeFriendFromGroup(phoneNo, friendPhoneNo);
        return ServiceResult.newSuccess();
    }

    /**
     * 添加好友
     * @param dto
     */
    @Override
    public ServiceResult<Object> addFriendInfo(FriendsInfoDto dto) {
        FriendsInfoEntity entity = CommonUtils.transform(dto, FriendsInfoEntity.class);
        friendsInfoDao.addFriendInfo(entity);
        return ServiceResult.newSuccess();
    }

}
