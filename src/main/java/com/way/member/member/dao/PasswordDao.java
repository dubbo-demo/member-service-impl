package com.way.member.member.dao;

import com.way.member.member.entity.PasswordPo;
import org.apache.ibatis.annotations.Param;

/**
 * 功能描述：会员密码Dao
 *
 * @ClassName PasswordDao
 * @author: xinpei.xu
 * @date: 2017/08/20 20:28
 */
public interface PasswordDao {
	
	Long insert(PasswordPo record);
	
	/**
	 * @Title: queryCurPasswdById
	 * @Description: 查询当前登录密码是否正确
	 * @return: String 返回mobile
	 */
	public String queryCurPasswdById(@Param("memberId") Long memberId, @Param("curPasssword") String curPasssword);

	/**
	 * @Title: queryPasswdById
	 * @Description: 根据会员编号查询信息
	 * @return: String 返回mobile
	 */
	public String queryPasswdById(@Param("memberId") Long memberId);
}