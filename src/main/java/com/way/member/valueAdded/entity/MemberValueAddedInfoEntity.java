package com.way.member.valueAdded.entity;

import com.way.common.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: 用户增值服务信息Entity
 * @author: xinpei.xu
 */
@Data
@ToString
@EqualsAndHashCode
public class MemberValueAddedInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 4113890243968230405L;

    private String invitationCode;// 邀请码

    private Integer type;// 增值服务类型:1:轨迹回放,2:电子围栏

    private Integer isOpen;// 是否开启:1:是,2:否,3:试用

    private Date startTime;// 开始时间

    private Date endTime;// 结束时间
}
