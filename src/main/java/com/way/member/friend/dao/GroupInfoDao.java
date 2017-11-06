package com.way.member.friend.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.friend.dto.GroupInfoDto;
import com.way.member.friend.entity.GroupInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: GroupInfoDao
 * @Description: 组信息Dao
 * @author xinpei.xu
 * @date 2017/08/29 21:22
 *
 */
public interface GroupInfoDao  extends IBaseMapper{

    /**
     * 新建组
     * @param phoneNo
     * @param groupName
     */
    void addGroupInfo(@Param("phoneNo") String phoneNo, @Param("groupName") String groupName);

    /**
     * 查出组信息
     * @param groupId
     * @return
     */
    GroupInfoEntity getGroupInfo(String groupId);

    /**
     * 修改组信息
     * @param dto
     */
    void modifyGroupInfo(GroupInfoDto dto);

    /**
     * 删除组信息
     * @param groupId
     */
    void deleteGroupInfo(String groupId);

    /**
     * 查询组信息
     * @param phoneNo
     * @return
     */
    List<GroupInfoEntity> getGroupInfoListByPhoneNo(String phoneNo);
}
