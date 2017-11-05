package com.way.member.friend.service;

import com.way.common.constant.Constants;
import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.member.friend.dao.ApplyFriendInfoDao;
import com.way.member.friend.dto.FriendsInfoDto;
import com.way.member.friend.entity.FriendsInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 增加被申请记录
     * @param phoneNo
     * @param friendPhoneNo
     * @param applyInfo
     */
    @Override
    public void applyForAddFriend(String phoneNo, String friendPhoneNo, String applyInfo) {
        applyFriendInfoDao.applyForAddFriend(phoneNo, friendPhoneNo, applyInfo);
    }

    /**
     * 获取被申请好友记录
     * @param phoneNo
     * @return
     */
    @Override
    public List<FriendsInfoDto> getApplicationRecordOfFriend(String phoneNo) {
        return applyFriendInfoDao.getApplicationRecordOfFriend(phoneNo);
    }

    /**
     * 同意/拒绝添加好友申请
     * @param phoneNo
     * @param friendPhoneNo
     * @param isApprove
     * @param applicationId
     * @return
     */
    @Override
    @Transactional
    public ServiceResult<Object> agreeToAddFriend(String phoneNo, String friendPhoneNo, String isApprove, String applicationId) {
        // 如果通过则互为好友
        if(isApprove.equals(Constants.YES)){
            FriendsInfoDto dto = new FriendsInfoDto();
            // 添加好友
            dto.setPhoneNo(phoneNo);// 手机号
            dto.setFriendPhoneNo(friendPhoneNo);// 好友手机号
            dto.setFriendRemarkName(friendPhoneNo);// 好友备注名
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
            dto.setPhoneNo(friendPhoneNo);// 手机号
            dto.setFriendPhoneNo(phoneNo);// 好友手机号
            dto.setFriendRemarkName(phoneNo);// 好友备注名
            friendsInfoService.addFriendInfo(dto);
        }
        applyFriendInfoDao.agreeToAddFriend(applicationId, isApprove);
        return ServiceResult.newSuccess();
    }

}
