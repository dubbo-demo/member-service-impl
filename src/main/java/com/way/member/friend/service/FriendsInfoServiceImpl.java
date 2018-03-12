package com.way.member.friend.service;

import com.way.common.constant.Constants;
import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.member.friend.dao.FriendsInfoDao;
import com.way.member.friend.dto.FriendsInfoDto;
import com.way.member.friend.dto.GroupInfoDto;
import com.way.member.friend.entity.FriendsInfoEntity;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.service.MemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 出退出前查看的好友信息
     * @param invitationCode
     * @return
     */
    @Override
    public List<FriendsInfoDto> getFriendsInfoBeforeExit(String invitationCode) {
        return friendsInfoDao.getFriendsInfoBeforeExit(invitationCode);
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
     * @param invitationCode
     * @param groupId
     * @param state
     */
    @Override
    public void updateIsCheckBeforeExitByGroupId(String invitationCode, String groupId, Integer state) {
        // 查询用户信息
//        ServiceResult<MemberDto> memberDto = memberInfoService.getMemberInfo(phoneNo);
//        String invitationCode = memberDto.getData().getInvitationCode();
        friendsInfoDao.updateIsCheckBeforeExitByGroupId(invitationCode, groupId, state);
    }

    /**
     * 取消查看好友实时坐标
     * @param invitationCode
     * @param friendInvitationCodes
     * @param state
     */
    @Override
    public void updateIsCheckBeforeExitByFriendPhoneNos(String invitationCode, List<String> friendInvitationCodes, Integer state) {
//        List<String> friendInvitationCodes = new ArrayList<>();
//
//        // 查询用户信息
//        ServiceResult<MemberDto> memberDto = memberInfoService.getMemberInfo(phoneNo);
//        String invitationCode = memberDto.getData().getInvitationCode();
//        for(String friendPhoneNo : friendPhoneNoList){
//            ServiceResult<MemberDto> friendMemberDto = memberInfoService.getMemberInfo(friendPhoneNo);
//            if(null != friendMemberDto.getData()){
//                friendInvitationCodes.add(friendMemberDto.getData().getInvitationCode());
//            }
//        }
        friendsInfoDao.updateIsCheckBeforeExitByFriendInvitationCodes(invitationCode, friendInvitationCodes, state);
    }

    /**
     * 查询好友信息
     * @param invitationCode
     * @param friendInvitationCode
     * @return
     */
    @Override
    public ServiceResult<FriendsInfoDto> getFriendInfo(String invitationCode, String friendInvitationCode) {
        ServiceResult<FriendsInfoDto> serviceResult = ServiceResult.newSuccess();
        FriendsInfoDto friendsInfoDto = friendsInfoDao.getFriendInfo(invitationCode, friendInvitationCode);
        serviceResult.setData(friendsInfoDto);
        return serviceResult;
    }

    /**
     * 查询好友列表
     * @param invitationCode
     * @return
     */
    @Override
    public List<FriendsInfoDto> getFriendList(String invitationCode) {
        return friendsInfoDao.getFriendList(invitationCode);
    }

    /**
     * 修改好友信息
     * @param invitationCode
     * @param dto
     */
    @Override
    public void modifyFriendInfo(String invitationCode, FriendsInfoDto dto) {
        FriendsInfoEntity entity = new FriendsInfoEntity();
        entity.setInvitationCode(invitationCode);
        entity.setFriendInvitationCode(dto.getFriendInvitationCode());
        entity.setFriendRemarkName(dto.getFriendRemarkName());
        entity.setIsAccreditVisible(dto.getIsAccreditVisible());
        entity.setAccreditStartTime(dto.getAccreditStartTime());
        entity.setAccreditEndTime(dto.getAccreditEndTime());
        entity.setAccreditWeeks(dto.getAccreditWeeks());
        friendsInfoDao.modifyFriendInfo(entity);
    }

    /**
     * 修改被授权人好友信息
     * @param invitationCode
     * @param dto
     */
    @Override
    public void modifyAuthorizedFriendInfo(String invitationCode, FriendsInfoDto dto) {
        FriendsInfoEntity entity = new FriendsInfoEntity();
        entity.setInvitationCode(invitationCode);
        entity.setFriendInvitationCode(dto.getFriendInvitationCode());
        entity.setIsAuthorizedVisible(dto.getIsAccreditVisible());
        entity.setAuthorizedAccreditStartTime(dto.getAccreditStartTime());
        entity.setAuthorizedAccreditEndTime(dto.getAccreditEndTime());
        entity.setAuthorizedWeeks(dto.getAccreditWeeks());
        friendsInfoDao.modifyAuthorizedFriendInfo(entity);
    }

    /**
     * 删除好友
     * @param invitationCode
     * @param friendInvitationCode
     */
    @Override
    public void deleteFriend(String invitationCode, String friendInvitationCode) {
        friendsInfoDao.deleteFriend(invitationCode, friendInvitationCode);
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
     * @param invitationCode
     * @param groupId
     */
    @Override
    public void updateFriendsGroupInfo(String invitationCode, String groupId) {
        friendsInfoDao.updateFriendsGroupInfo(invitationCode, groupId);
    }

    /**
     * 将好友添加到分组
     * @param friendInvitationCodes
     * @param groupInfoDto
     */
    @Override
    @Transactional
    public void moveFriendToGroup(String friendInvitationCodes, GroupInfoDto groupInfoDto) {
        for(String friendInvitationCode : friendInvitationCodes.split("\\|")){
//            ServiceResult<MemberDto> friendMemberDto = memberInfoService.getMemberInfo(friendInvitationCode);
            friendsInfoDao.moveFriendToGroup(friendInvitationCode, groupInfoDto);
        }
    }

    /**
     * 将好友从分组中移除
     * @param invitationCode
     * @param friendInvitationCodes
     * @return
     */
    @Override
    @Transactional
    public ServiceResult<Object> removeFriendFromGroup(String invitationCode, String friendInvitationCodes) {
//        ServiceResult<MemberDto> memberDto = memberInfoService.getMemberInfo(phoneNo);
//        String invitationCode = memberDto.getData().getInvitationCode();
        for(String friendInvitationCode : friendInvitationCodes.split("\\|")){
//            ServiceResult<MemberDto> friendMemberDto = memberInfoService.getMemberInfo(friendPhoneNo);
            friendsInfoDao.removeFriendFromGroup(invitationCode, friendInvitationCode);
        }
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

    /**
     * 查询是否被好友授权可见
     * @param invitationCode
     * @param friendInvitationCode
     * @return
     */
    @Override
    public FriendsInfoDto checkIsAuthorizedVisible(String invitationCode, String friendInvitationCode) {
//        // 根据手机号查用户邀请码
//        ServiceResult<MemberDto> memberDto = memberInfoService.getMemberInfo(phoneNo);
//        String invitationCode = memberDto.getData().getInvitationCode();
//        // 根据手机号查用户邀请码
//        ServiceResult<MemberDto> friendMemberDto = memberInfoService.getMemberInfo(friendPhoneNo);
//        String friendInvitationCode = friendMemberDto.getData().getInvitationCode();

        return friendsInfoDao.checkIsAuthorizedVisible(invitationCode, friendInvitationCode);
    }

    /**
     * 设置好友为退出前可见
     * @param invitationCode
     * @param setInvisibleFriendsList
     * @param setVisibleFriendsList
     */
    @Override
    @Transactional
    public void setFriendsVisibleBeforeExiting(String invitationCode, List<String> setInvisibleFriendsList, List<String> setVisibleFriendsList) {
        List<String> friendInvitationCodes = new ArrayList<String>();
        // 标记好友退出前查看为是：1
        if(setVisibleFriendsList.size() > 0){
            for(String setVisibleFriend : setVisibleFriendsList){
//                ServiceResult<MemberDto> friendMemberDto = memberInfoService.getMemberInfo(setVisibleFriend);
                friendInvitationCodes.add(setVisibleFriend);
            }
            friendsInfoDao.updateIsCheckBeforeExitByFriendInvitationCodes(invitationCode, friendInvitationCodes, Constants.YES_INT);
        }
        // 标记好友退出前查看为否：2
        if(setInvisibleFriendsList.size() > 0){
            for(String setInvisibleFriend : setInvisibleFriendsList){
//                ServiceResult<MemberDto> friendMemberDto = memberInfoService.getMemberInfo(setInvisibleFriend);
                friendInvitationCodes.add(setInvisibleFriend);
            }
            friendsInfoDao.updateIsCheckBeforeExitByFriendInvitationCodes(invitationCode, friendInvitationCodes, Constants.NO_INT);
        }
    }

}
