package com.way.member.member.dao;


import com.way.common.rom.IBaseMapper;
import com.way.member.member.entity.RegistLogPo;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: RegistLogDao
 * @Description: 会员注册日志Dao
 * @author: xinpei.xu
 * @date: 2017/08/20 20:12
 *
 */
@Repository
public interface RegistLogDao extends IBaseMapper {
    
	Long insert(RegistLogPo record);
}