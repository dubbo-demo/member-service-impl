package com.way.member.member.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * 客户注册日志表
 * @author xinpei.xu
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegistLogPo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/** 客户id */
	private Long memberId;
	/** 设备号 */
	private String deviceNo;
	/** 版本号 */
	private String version;
	/** 推广渠道来源 */
	private String promotionSource;
	/** 终端渠道 */
	private String terminalChannel;
	/** app应用渠道 */
	private String appSource;
	/** lbs */
	private String lbs;
	/** 城市 */
	private String city;
}