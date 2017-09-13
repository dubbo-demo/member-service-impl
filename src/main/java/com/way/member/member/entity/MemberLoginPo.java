package com.way.member.member.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * 用户登录信息表
 * @author xinpei.xu
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberLoginPo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/** 客户id */
	private Long memberId;
	/** 设备号 */
	private String deviceNo;
	/** 登录IP */
	private String sIp;
	/** 城市 */
	private String city;
	/** lbs */
	private String lbs;
}
