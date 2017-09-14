package com.way.member.member.dao;


import com.way.member.member.entity.RegistLogPo;

/**
 * @ClassName: RegistLogDao
 * @Description: 会员注册日志Dao
 * @author: xinpei.xu
 * @date: 2017/08/20 20:12
 *
 */
public interface RegistLogDao {
    
	Long insert(RegistLogPo record);
}