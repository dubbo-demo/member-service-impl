package com.way.member.friend.service;

import com.way.common.result.ServiceResult;
import com.way.member.friend.dao.FriendsInfoDao;
import com.way.member.friend.dto.FriendsInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: FriendsInfoServiceImpl
 * @Description: 好友信息ServiceImp
 * @author xinpei.xu
 * @date 2017/08/23 19:14
 *
 */
@Service
public class FriendsInfoServiceImpl implements FriendsInfoService {

    @Autowired
    private FriendsInfoDao friendsInfoDao;

    /**
     * 出退出前查看的好友信息
     * @param phoneNo
     * @return
     */
    @Override
    public List<FriendsInfoDto> getFriendsInfoBeforeExit(String phoneNo) {
        return friendsInfoDao.getFriendsInfoBeforeExit(phoneNo);
    }

    /**
     * 根据组ID获取好友信息
     * @param phoneNo
     * @param groupId
     * @return
     */
    @Override
    public List<FriendsInfoDto> getRealtimePositionByGroupId(String phoneNo, String groupId) {
        return friendsInfoDao.getRealtimePositionByGroupId(phoneNo, groupId);
    }

    /**
     * 更新好友是否退出前查看状态
     * @param phoneNo
     * @param groupId
     * @param state
     */
    @Override
    public void updateIsCheckBeforeExitByGroupId(String phoneNo, String groupId, Integer state) {
        friendsInfoDao.updateIsCheckBeforeExitByGroupId(phoneNo, groupId, state);
    }

    /**
     * 取消查看好友实时坐标
     * @param phoneNo
     * @param friendPhoneNo
     * @param state
     */
    @Override
    public void updateIsCheckBeforeExitByFriendPhoneNo(String phoneNo, String friendPhoneNo, Integer state) {
        friendsInfoDao.updateIsCheckBeforeExitByFriendPhoneNo(phoneNo, friendPhoneNo, state);
    }

    /**
     * 查询好友信息
     * @param phoneNo
     * @param friendPhoneNo
     * @return
     */
    @Override
    public ServiceResult<FriendsInfoDto> getFriendInfo(String phoneNo, String friendPhoneNo) {
        return friendsInfoDao.getFriendInfo(phoneNo, friendPhoneNo);
    }


}
