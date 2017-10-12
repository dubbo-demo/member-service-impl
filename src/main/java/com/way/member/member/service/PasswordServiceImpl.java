package com.way.member.member.service;

import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.member.member.dao.PasswordDao;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.entity.PasswordPo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: PasswordServiceImpl
 * @Description: 会员密码PasswordServiceImpl
 * @author: xinpei.xu
 * @date: 2017/08/20 20:30
 * 
 */
@Service("passwordServiceImpl")
public class PasswordServiceImpl implements PasswordService {
    
	@Autowired
	private PasswordDao passwordDao;

	/**
	 * 保存密码
	 * @param memberDto
	 * @return
	 */
	public void savePasswordInfo(MemberDto memberDto) {
		PasswordPo passwordPo = CommonUtils.transform(memberDto, PasswordPo.class);
		passwordDao.insert(passwordPo);
	}
	
	/**
	 * @Title: queryCurPasswdById
	 * @Description: 查询当前登录密码是否正确
	 * @return: String 返回mobile
	 */
	public ServiceResult<String> queryCurPasswdById(Long memberId, String curPasssword){
		String id = passwordDao.queryCurPasswdById(memberId, curPasssword);
		if(StringUtils.isNotBlank(id)){
			return ServiceResult.newSuccess(id);
		}else{
			return ServiceResult.newSuccess();
		}
	}
	
}
