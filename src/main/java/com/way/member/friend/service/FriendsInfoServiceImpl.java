package com.way.member.friend.service;

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
}
