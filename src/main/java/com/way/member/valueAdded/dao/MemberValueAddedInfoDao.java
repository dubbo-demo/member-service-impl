package com.way.member.valueAdded.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.valueAdded.entity.MemberValueAddedInfoEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 用户增值服务信息Dao
 * @author: xinpei.xu
 */
public interface MemberValueAddedInfoDao extends IBaseMapper {

    /**
     * 根据增值服务类型获取用户增值服务信息
     * @param invitationCode
     * @param type
     * @return
     */
    MemberValueAddedInfoEntity getMemberValueAddedInfoByType(@Param("invitationCode") String invitationCode, @Param("type") String type);

    /**
     * 新增用户增值服务信息
     * @param entity
     */
    void saveMemberValueAddedInfo(MemberValueAddedInfoEntity entity);

    /**
     * 更新用户增值服务信息
     * @param entity
     */
    void updateMemberValueAddedInfo(MemberValueAddedInfoEntity entity);
}
