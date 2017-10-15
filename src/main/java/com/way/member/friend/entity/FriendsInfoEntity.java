package com.way.member.friend.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
}
