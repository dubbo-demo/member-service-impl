package com.way.member.friend.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName: GroupInfoEntity
 * @Description: 组信息Entity
 * @author xinpei.xu
 * @date 2017/08/29 21:25
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GroupInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 组id */
    private String groupId;

    /** 组名称 */
    private String groupName;

    /** 手机号 */
    private String phoneNo;

    /** 是否授权可见 1:是,2:否 */
    private Integer isAccreditVisible;

    /** 授权开始时间 yyyy-MM-dd HH:mm */
    private String accreditStartTime;

    /** 授权结束时间 yyyy-MM-dd HH:mm */
    private String accreditEndTime;

    /** 授权日期 */
    private String accreditWeeks;
}
