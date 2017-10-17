package com.way.member.friend.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName: FriendsInfoEntity
 * @Description: 好友信息Entity
 * @author xinpei.xu
 * @date 2017/08/23 19:14
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FriendsInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 手机号 */
    private String phoneNo;//

    /** 好友手机号 */
    private String friendPhoneNo;//

    /** 好友备注名 */
    private String friendRemarkName;//

    /** 是否授权可见 1:是,2:否 */
    private Integer isAccreditVisible;//

    /** 授权开始时间 */
    private Date accreditStartTime;//

    /** 授权结束时间 */
    private Date accreditEndTime;//

    /** 是否被授权可见 1:是,2:否 */
    private Integer isAuthorizedVisible;//

    /** 被授权开始时间 */
    private Date authorizedAccreditStartTime;//

    /** 被授权结束时间 */
    private Date authorizedAccreditEndTime;//

    /** 组id */
    private String groupId;//

    /** 组名称 */
    private String groupName;//

    /** 组创建时间 */
    private Date groupCreateTime;//

    /** 是否退出前查看 1:是,2:否 */
    private Integer isCheckBeforeExit;//
}
