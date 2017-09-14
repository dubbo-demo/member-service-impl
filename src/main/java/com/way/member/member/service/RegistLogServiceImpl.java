package com.way.member.member.service;

import com.alibaba.fastjson.JSON;
import com.way.common.util.CommonUtils;
import com.way.member.member.dao.RegistLogDao;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.entity.RegistLogPo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RegistLogServiceImpl
 * @Description: 会员注册日志ServiceImpl
 * @author: xinpei.xu
 * @date: 2017/08/20 21:56
 *
 */
@Service
public class RegistLogServiceImpl implements RegistLogService {

//	@Autowired
	private RegistLogDao registLogDao;

	public Long saveRegistLog(MemberDto memberDto) {
		RegistLogPo registLogPo = CommonUtils.transform(memberDto, RegistLogPo.class);
		Map<String, Object> lbsMap = new HashMap<>();
		lbsMap.put("lng", memberDto.getLng());
		lbsMap.put("lat", memberDto.getLat());
		registLogPo.setLbs(JSON.toJSONString(lbsMap));
		return registLogDao.insert(registLogPo);
	}

}
