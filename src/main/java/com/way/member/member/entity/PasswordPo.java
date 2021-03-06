package com.way.member.member.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * 客户密码管理表
 * @author xinpei.xu
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PasswordPo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/** 邀请码 */
	private String invitationCode;
	/** 客户密码 */
	private String password;
}
