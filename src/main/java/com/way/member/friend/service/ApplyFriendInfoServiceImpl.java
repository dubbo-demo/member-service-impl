package com.way.member.friend.service;

import com.way.common.result.ServiceResult;
import com.way.member.friend.dao.ApplyFriendInfoDao;
import com.way.member.friend.dto.FriendsInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @return
     */
    @Override
    public ServiceResult<Object> agreeToAddFriend(String phoneNo, String friendPhoneNo, String isApprove) {
        applyFriendInfoDao.agreeToAddFriend(phoneNo, friendPhoneNo, isApprove);
        return ServiceResult.newSuccess();
    }
}
