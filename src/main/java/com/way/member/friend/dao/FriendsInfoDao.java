package com.way.member.friend.dao;

import com.way.member.friend.dto.FriendsInfoDto;

import java.util.List;

/**
 * @ClassName: FriendsInfoDao
 * @Description: 好友信息Dao
 * @author xinpei.xu
 * @date 2017/08/23 19:14
 *
 */
public interface FriendsInfoDao {

    /**
     * 出退出前查看的好友信息
     * @param phoneNo
     * @return
     */
    List<FriendsInfoDto> getFriendsInfoBeforeExit(String phoneNo);
}
