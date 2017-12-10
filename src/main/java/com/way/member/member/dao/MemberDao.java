package com.way.member.member.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.entity.MemberInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 功能描述：用户信息Dao
 *
 * @ClassName MemberDao
 * @Author：xinpei.xu
 * @Date：2017/08/20 22:16
 */
public interface MemberDao extends IBaseMapper {

	/**
	 * 保存用户信息
	 * @param record
	 * @return
	 */
	Long insert(MemberInfoEntity record);
	
	/**
	 * @Title: selectUserInfoByMobile
	 * @Description: 根据手机号查询用户信息
	 * @return: Map<String,Object>
	 */
	MemberInfoEntity selectUserInfoByMobile(String phoneNo);
	
	/**
	 * @Title: queryMemberInfo
	 * @Description: 根据手机号查询会员信息
	 * @return: Map<String, String>
	 */
	public MemberInfoEntity queryMemberInfo(String phoneNo);
	
	/**
	 * @Title: updatePassword
	 * @Description: 修改密码
	 * @return: void
	 */
	public void updatePassword(@Param("phoneNo") String phoneNo, @Param("newPassword") String newPassword);

	/**
	 * 根据手机号更新用户头像id
	 * @param phoneNo
	 * @param headPicId
	 */
	void updateHeadPicIdByPhoneNo(@Param("phoneNo") String phoneNo, @Param("headPicId") String headPicId);

	/**
	 * 根据手机号搜索用户
	 * @param phoneNo
	 * @return
	 */
	MemberInfoEntity searchUserByPhoneNo(String phoneNo);

	/**
	 * 查看个人信息
	 * @param phoneNo
	 * @return
	 */
	MemberInfoEntity getMemberInfo(String phoneNo);

	/**
	 * 修改个人信息
	 * @param dto
	 */
	void modifyMemberInfo(MemberDto dto);

	/**
	 * 更新推荐人总积分
	 * @param phoneNo
	 * @param rewardScore
	 */
    void addRewardScore(@Param("phoneNo") String phoneNo, @Param("rewardScore") Integer rewardScore);

	/**
	 * 扣除积分并且给用户设置为会员/或者延期会员
	 * @param phoneNo
	 * @param rewardScore
	 * @param startTime
	 * @param endTime
	 */
	void minusMemberTypeInfo(@Param("phoneNo") String phoneNo, @Param("rewardScore") Integer rewardScore, @Param("memberType") Integer memberType,
							 @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}