package com.way.member.friend.dao;

import com.way.member.friend.dto.FriendsInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: ApplyFriendInfoDao
 * @Description: 申请好友信息Dao
 * @author xinpei.xu
 * @date 2017/09/06 20:21
 *
 */
public interface ApplyFriendInfoDao {

    /**
     * 增加被申请记录
     * @param phoneNo
     * @param friendPhoneNo
     * @param applyInfo
     */
    void applyForAddFriend(@Param("phoneNo") String phoneNo, @Param("friendPhoneNo") String friendPhoneNo, @Param("applyInfo") String applyInfo);

    /**
     * 获取被申请好友记录
     * @param phoneNo
     * @return
     */
    List<FriendsInfoDto> getApplicationRecordOfFriend(String phoneNo);


    /**
     * 同意/拒绝添加好友申请
     * @param phoneNo
     * @param friendPhoneNo
     * @param isApprove
     * @return
     */
    void agreeToAddFriend(@Param("phoneNo") String phoneNo, @Param("friendPhoneNo") String friendPhoneNo, @Param("isApprove") String isApprove);
}
