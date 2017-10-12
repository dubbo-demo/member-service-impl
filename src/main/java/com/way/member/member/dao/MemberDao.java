package com.way.member.member.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.member.entity.MemberInfoEntity;
import org.apache.ibatis.annotations.Param;

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
	public MemberInfoEntity queryMemberInfo(String mobile);
	
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
}