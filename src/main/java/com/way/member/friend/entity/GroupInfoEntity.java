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

    /** 组名称 */
    private String groupName;

    /** 手机号 */
    private String phoneNo;

}
