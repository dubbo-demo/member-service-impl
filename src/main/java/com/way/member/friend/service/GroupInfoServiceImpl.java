package com.way.member.friend.service;

import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.member.friend.dao.GroupInfoDao;
import com.way.member.friend.dto.GroupInfoDto;
import com.way.member.friend.entity.GroupInfoEntity;
import com.way.member.member.entity.MemberInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: GroupInfoServiceImpl
 * @Description: 组信息ServiceImpl
 * @author xinpei.xu
 * @date 2017/08/29 21:19
 *
 */
@Service
public class GroupInfoServiceImpl implements GroupInfoService {

    @Autowired
    private GroupInfoDao groupInfoDao;

    /**
     * 新建组
     * @param invitationCode
     * @param groupName
     * @return
     */
    @Override
    public ServiceResult<Object> addGroupInfo(String invitationCode, String groupName) {
        groupInfoDao.addGroupInfo(invitationCode, groupName, new Date());
        return ServiceResult.newSuccess();
    }

    /**
     * 查出组信息
     * @param groupId
     * @return
     */
    @Override
    public GroupInfoDto getGroupInfo(String groupId) {
        GroupInfoEntity entity = groupInfoDao.getGroupInfo(groupId);
        return CommonUtils.transform(entity, GroupInfoDto.class);
    }

    /**
     * 修改组信息
     * @param dto
     */
    @Override
    public void modifyGroupInfo(GroupInfoDto dto) {
        groupInfoDao.modifyGroupInfo(dto);
    }

    /**
     * 删除组信息
     * @param groupId
     */
    @Override
    public void deleteGroupInfo(String groupId) {
        groupInfoDao.deleteGroupInfo(groupId);
    }

    /**
     * 查询组信息
     * @param invitationCode
     * @return
     */
    @Override
    public List<GroupInfoDto> getGroupInfoListByInvitationCode(String invitationCode) {
        List<GroupInfoEntity> entity = groupInfoDao.getGroupInfoListByInvitationCode(invitationCode);
        return CommonUtils.transformList(entity, GroupInfoDto.class);
    }

}
