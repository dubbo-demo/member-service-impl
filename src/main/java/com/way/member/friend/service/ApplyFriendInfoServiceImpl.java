package com.way.member.friend.service;

import com.way.common.constant.Constants;
import com.way.common.result.ServiceResult;
import com.way.member.friend.dao.ApplyFriendInfoDao;
import com.way.member.friend.dto.FriendsInfoDto;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.service.MemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ApplyFriendInfoServiceImpl
 * @Description: 申请好友信息ServiceImpl
 * @author xinpei.xu
 * @date 2017/09/06 20:19
 *
 */
@Service
public class ApplyFriendInfoServiceImpl implements ApplyFriendInfoService {

    @Autowired
    private ApplyFriendInfoDao applyFriendInfoDao;

    @Autowired
    private FriendsInfoService friendsInfoService;

    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 申请添加好友
     * @param invitationCode
     * @param friendInvitationCode
     * @param applyInfo
     */
    @Override
    public void applyForAddFriend(String invitationCode, String friendInvitationCode, String applyInfo) {
        // 查询被申请人是否有被申请记录
        int count = applyFriendInfoDao.getAddFriendInfo(invitationCode, friendInvitationCode);
        // 有更新没有新增
        if(count == 0){
            // 增加被申请记录
            applyFriendInfoDao.addApplyFriendInfo(invitationCode, friendInvitationCode, applyInfo, new Date(), new Date());
        }else{
            // 更新被申请记录
            applyFriendInfoDao.updateApplyFriendInfo(invitationCode, friendInvitationCode, applyInfo, new Date());
        }
    }

    /**
     * 获取被申请好友记录
     * @param invitationCode
     * @return
     */
    @Override
    public List<FriendsInfoDto> getApplicationRecordOfFriend(String invitationCode) {
        return applyFriendInfoDao.getApplicationRecordOfFriend(invitationCode);
    }

    /**
     * 同意/拒绝添加好友申请
     * @param invitationCode
     * @param friendInvitationCode
     * @param isApprove
     * @param applicationId
     * @return
     */
    @Override
    @Transactional
    public ServiceResult<Object> agreeToAddFriend(String invitationCode, String friendInvitationCode, String isApprove, String applicationId) {
//        ServiceResult<MemberDto> memberDto = memberInfoService.getMemberInfo(phoneNo);
//        String invitationCode = memberDto.getData().getInvitationCode();
        ServiceResult<MemberDto> friendMemberDto = memberInfoService.getMemberInfo(friendInvitationCode);
        if(null == friendMemberDto.getData()){
            ServiceResult.newFailure("该用户不存在");
        }
//        String friendInvitationCode = friendMemberDto.getData().getInvitationCode();

        // 如果通过则互为好友
        if(isApprove.equals(Constants.YES)){
            // 校验双方是否互为好友
            ServiceResult<FriendsInfoDto> friendsInfoDto = friendsInfoService.getFriendInfo(invitationCode, friendInvitationCode);
            if(null == friendsInfoDto.getData()){
                FriendsInfoDto dto = new FriendsInfoDto();
                // 添加好友
//                dto.setPhoneNo(phoneNo);// 手机号
//                dto.setFriendPhoneNo(friendPhoneNo);// 好友手机号
                dto.setInvitationCode(invitationCode);// 邀请码
                dto.setFriendInvitationCode(friendInvitationCode);// 好友邀请码
                dto.setFriendRemarkName(memberInfoService.getMemberInfo(friendInvitationCode).getData().getNickName());// 好友备注名
                dto.setIsAccreditVisible(Constants.YES_INT);// 是否授权可见 1:是,2:否
                dto.setAccreditStartTime(Constants.ACCREDIT_STARTTIME);// 授权开始时间
                dto.setAccreditEndTime(Constants.ACCREDIT_ENDTIME);// 授权结束时间
                dto.setAccreditWeeks(Constants.WEEKS);// 授权日期
                dto.setIsAuthorizedVisible(Constants.YES_INT);// 是否被授权可见 1:是,2:否
                dto.setAuthorizedAccreditStartTime(Constants.ACCREDIT_STARTTIME);// 被授权开始时间
                dto.setAuthorizedAccreditEndTime(Constants.ACCREDIT_ENDTIME);// 被授权结束时间
                dto.setAuthorizedWeeks(Constants.WEEKS);// 被授权日期
                dto.setIsCheckBeforeExit(Constants.NO_INT);// 是否退出前查看 1:是,2:否
                friendsInfoService.addFriendInfo(dto);
                // 申请人好友列表添加数据
//                dto.setPhoneNo(friendPhoneNo);// 手机号
//                dto.setFriendPhoneNo(phoneNo);// 好友手机号

                dto.setInvitationCode(friendInvitationCode);// 邀请码
                dto.setFriendInvitationCode(invitationCode);// 好友邀请码
                dto.setFriendRemarkName(memberInfoService.getMemberInfo(invitationCode).getData().getNickName());// 好友备注名
                friendsInfoService.addFriendInfo(dto);
            }
        }
        applyFriendInfoDao.agreeToAddFriend(applicationId, isApprove);
        return ServiceResult.newSuccess();
    }

}
