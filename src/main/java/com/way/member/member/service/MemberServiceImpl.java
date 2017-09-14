package com.way.member.member.service;

import com.alibaba.fastjson.JSON;
import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.member.member.dao.MemberDao;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.entity.MemberLoginPo;
import com.way.member.member.entity.MemberPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MemberServiceImpl
 * @Description: 会员ServiceImpl
 * @author: xinpei.xu
 * @date: 2017/08/20 21:53
 *
 */
@Service
public class MemberServiceImpl implements MemberService {

//	@Autowired
	private MemberDao memberDao;
	@Autowired
	private RegistLogService registLogService;
	@Autowired
	private PasswordService passwordService;

	public Long saveMemberInfo(MemberDto memberDto) {
		MemberPo memberPo = CommonUtils.transform(memberDto, MemberPo.class);
		memberDao.insert(memberPo);
		return memberPo.getId();
	}

	/**
	 * @Title: loadMapByMobile
	 * @Description: 根据手机号查询用户信息
	 * @return: Map<String,Object>
	 */
	public ServiceResult<MemberDto> loadMapByMobile(String mobile) {
		MemberPo memberPo = memberDao.selectUserInfoByMobile(mobile);
		if(memberPo != null){
			MemberDto memberDto = CommonUtils.transform(memberPo, MemberDto.class);
			return ServiceResult.newSuccess(memberDto);
		}else{
			return ServiceResult.newSuccess(null);
		}
	}

	/**
	 * @Title: selectIsOldMember
	 * @Description: 根据手机号查询该手机号是否是老用户
	 * @return: Map<String,Object>
	 */
	public ServiceResult<MemberDto> selectIsOldMember(String mobile) {
		MemberPo memberPo = memberDao.selectIsOldMember(mobile);
		if(memberPo != null){
			MemberDto memberDto = CommonUtils.transform(memberPo, MemberDto.class);
			return ServiceResult.newSuccess(memberDto);
		}else{
			return ServiceResult.newSuccess(null);
		}
	}

	/**
	 * @Title: queryMemberInfo
	 * @Description: 根据手机号查询会员信息
	 * @return: Map<String, String>
	 */
	public ServiceResult<MemberDto> queryMemberInfo(String mobile){
		MemberPo memberPo = memberDao.queryMemberInfo(mobile);
		if(memberPo != null){
			MemberDto memberDto = CommonUtils.transform(memberPo, MemberDto.class);
			return ServiceResult.newSuccess(memberDto);
		}else{
			return ServiceResult.newSuccess(null);
		}
	}

	/**
	 * @Title: saveMemberLoginInfo
	 * @Description: 保存用户登录信息
	 * @return: void
	 */
	@Async
	public void saveMemberLoginInfo(MemberDto memberDto){
		MemberLoginPo memberLoginPo = CommonUtils.transform(memberDto, MemberLoginPo.class);
		Map<String, Object> lbsMap = new HashMap<>();
		lbsMap.put("lng", memberDto.getLng());
		lbsMap.put("lat", memberDto.getLat());
		memberLoginPo.setLbs(JSON.toJSONString(lbsMap));
		memberDao.saveLoginInfo(memberLoginPo);
	}

	/**
	 * @Title: updatePassword
	 * @Description: 忘记密码
	 * @return: void
	 */
	public void updatePassword(Long memberId, String newPassword) {
		memberDao.updatePassword(memberId, newPassword);
	}

	/**
	 * 用户注册数据保存
	 * @param memberDto
	 * @return
	 */
	@Transactional
	public ServiceResult<MemberDto> memberRegist(MemberDto memberDto){
		// 保存客户信息表
		Long memberId = saveMemberInfo(memberDto);
		memberDto.setMemberId(memberId);
		// 保存密码表
		passwordService.savePasswordInfo(memberDto);
		// 保存注册日志表
		registLogService.saveRegistLog(memberDto);
		return ServiceResult.newSuccess(memberDto);
	}

	/**
	 * 获取判断用户来源
	 * @param memberId
	 * @return
	 */
	@Override
	public MemberDto queryMemberSourceByMemberId(Long memberId) {
		return memberDao.queryMemberSourceByMemberId(memberId);
	}

}
