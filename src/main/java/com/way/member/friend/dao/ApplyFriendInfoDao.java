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
     * @param phoneNo
     * @param friendPhoneNo
     */
    int getAddFriendInfo(@Param("phoneNo") String phoneNo, @Param("friendPhoneNo") String friendPhoneNo);

    /**
     * 增加被申请记录
     * @param phoneNo
     * @param friendPhoneNo
     * @param applyInfo
     * @param createTime
     * @param modifyTime
     */
    void addApplyFriendInfo(@Param("phoneNo") String phoneNo, @Param("friendPhoneNo") String friendPhoneNo, @Param("applyInfo") String applyInfo, @Param("createTime") Date createTime, @Param("modifyTime") Date modifyTime);

    /**
     * 更新被申请记录
     * @param phoneNo
     * @param friendPhoneNo
     * @param applyInfo
     * @param modifyTime
     */
    void updateApplyFriendInfo(@Param("phoneNo") String phoneNo, @Param("friendPhoneNo") String friendPhoneNo, @Param("applyInfo") String applyInfo, @Param("modifyTime") Date modifyTime);

    /**
     * 获取被申请好友记录
     * @param phoneNo
     * @return
     */
    List<FriendsInfoDto> getApplicationRecordOfFriend(String phoneNo);


    /**
     * 同意/拒绝添加好友申请
     * @param applicationId
     * @param isApprove
     * @return
     */
    void agreeToAddFriend(@Param("applicationId") String applicationId, @Param("isApprove") String isApprove);

}
