package com.way.member.member.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 
 * 客户信息表
 * @author xinpei.xu
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberInfoEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/** 客户手机号 */
	private String phoneNo;
	/** 客户姓名 */
	private String nickName;
	/** 昵称拼音 */
	private String nickSpell;
	/** 性别1男,2女 */
	private String gender;
	/** 会员类型 1:非会员,2:正式会员,3:试用期会员 */
	private String memberType;
	/** 是否开通增值服务1是2否 */
	private String valueAddedService;
	/** 年龄 */
	private String age;
	/** 头像ID */
	private String headPic;
	/** 会员开始时间 */
	private Date memberStartTime;
	/** 会员结束时间 */
	private Date memberEndTime;
	/** 增值服务开始时间 */
	private Date valueAddedServiceStartTime;
	/** 增值服务结束时间 */
	private Date valueAddedServiceEndTime;
	/** 积分 */
	private String rewardScore;
	/** 邀请码 */
	private String invitationCode;

}