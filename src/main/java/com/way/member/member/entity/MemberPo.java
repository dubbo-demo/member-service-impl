package com.way.member.member.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * 客户信息表
 * @author xinpei.xu
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberPo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/** 客户手机号 */
	private String phone;
	/** 客户密码 */
	private String password;
	/** 员工编号 */
	private String employeeId;
	/** 客户来源 */
	private int memberSource;
	/** 客户ID */
	private Long memberId;
	/** 客户姓名 */
	private String memberName;
	/** 资料完善度 */
	private String dataCompletValues;
	/** 资料明细完成步骤 */
	private String datadetailcompletvalues;
	/** 身份证号 **/
	private String idCarNo;
}