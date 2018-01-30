package com.way.member.member.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName: InviteRelationshipInfoEntity
 * @Description: 邀请码关系信息Entity
 * @author: xinpei.xu
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class InviteRelationshipInfoEntity extends BaseEntity {

    private static final long serialVersionUID = -7801823783083551954L;

    private String invitationCode; // 自己的邀请码

    private String nextLevelInvitationCode; // 下一级用户邀请码

    private String underNextLevelInvitationCode; // 下下级用户邀请码
}
