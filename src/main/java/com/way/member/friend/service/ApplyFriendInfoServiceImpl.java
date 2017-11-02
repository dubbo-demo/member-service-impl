package com.way.member.friend.service;

import com.way.common.constant.Constants;
import com.way.common.result.ServiceResult;
import com.way.member.friend.dao.ApplyFriendInfoDao;
import com.way.member.friend.dto.FriendsInfoDto;
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
            // 加好友

            // 添加好友

        }
        applyFriendInfoDao.agreeToAddFriend(applicationId, isApprove);
        return ServiceResult.newSuccess();
    }
}
