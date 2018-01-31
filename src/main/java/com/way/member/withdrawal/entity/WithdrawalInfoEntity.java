package com.way.member.withdrawal.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 功能描述：提现信息Entity
 *
 * @Author：xinpei.xu
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WithdrawalInfoEntity extends BaseEntity {

    private static final long serialVersionUID = -1465098103115202016L;

    private String phoneNo;// 手机号

    private String bankName;// 银行名称

    private String bankNumber;// 银行卡号

    private String name;// 开户人姓名

    private String bankBranch;// 开户行支行

    private Double rewardScore;// 积分

    private String remark;// 备注

    private Integer status;// 状态 0:待处理,1:提醒成功,2:提现失败

    private String invitationCode;// 邀请码
}
