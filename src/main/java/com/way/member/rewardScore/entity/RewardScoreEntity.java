package com.way.member.rewardScore.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 功能描述：积分Entity
 *
 * @Author：xinpei.xu
 * @Date：2017/10/14 10:12
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RewardScoreEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String invitationCode; // 邀请码

    private Integer rewardScoreType;// 积分类型:1:购买会员,2:推荐奖励,3:提现,4:转增

    private String detailInfo; // 积分使用/获得详情

    private Double rewardScore; // 使用/获得积分数

}
