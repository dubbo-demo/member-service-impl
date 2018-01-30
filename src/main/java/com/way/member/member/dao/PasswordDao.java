package com.way.member.member.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.member.entity.PasswordPo;
import org.apache.ibatis.annotations.Param;

/**
 * 功能描述：会员密码Dao
 *
 * @ClassName PasswordDao
 * @author: xinpei.xu
 * @date: 2017/08/20 20:28
 */
public interface PasswordDao extends IBaseMapper {

	/**
	 * 保存密码
	 * @param record
	 * @return
	 */
	Long insert(PasswordPo record);
	
	/**
	 * @Title: queryCurPasswdById
	 * @Description: 查询当前登录密码是否正确
	 * @return: String 返回mobile
	 */
	public Integer checkCurPassword(@Param("invitationCode") String invitationCode, @Param("curPasssword") String curPasssword);
}