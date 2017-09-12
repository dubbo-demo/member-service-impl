package com.way.member.member.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.entity.MemberLoginPo;
import com.way.member.member.entity.MemberPo;
import org.apache.ibatis.annotations.Param;

/**
 * 功能描述：用户信息Dao
 *
 * @ClassName MemberDao
 * @Author：xinpei.xu
 * @Date：2017/08/20 22:16
 */
public interface MemberDao extends IBaseMapper {
    
	Long insert(MemberPo record);
	
	/**
	 * @Title: selectUserInfoByMobile
	 * @Description: 根据手机号查询用户信息
	 * @return: Map<String,Object>
	 */
	MemberPo selectUserInfoByMobile(String mobile);
	
	/**
	 * @Title: selectIsOldMember
	 * @Description: 根据手机号查询该手机号是否是老用户
	 * @return: Map<String,Object>
	 */
	MemberPo selectIsOldMember(String mobile);
	
	/**
	 * @Title: queryMemberInfo
	 * @Description: 根据手机号查询会员信息
	 * @return: Map<String, String>
	 */
	public MemberPo queryMemberInfo(String mobile);
	
	/**
	 * @Title: saveLoginInfo
	 * @Description: 保存用户登录信息
	 * @return: void
	 */
	public void saveLoginInfo(MemberLoginPo memberLoginPo);
	
	/**
	 * @Title: updatePassword
	 * @Description: 修改密码
	 * @return: void
	 */
	public void updatePassword(@Param("memberId") Long memberId, @Param("newPassword") String newPassword);

	/**
	 * 获取判断用户来源
	 * @param memberId
	 * @return
	 */
	MemberDto queryMemberSourceByMemberId(Long memberId);
}