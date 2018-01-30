package com.way.member.member.service;

import com.way.member.member.dao.InviteRelationshipInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: InviteRelationshipInfoServiceImpl
 * @Description: 邀请码关系信息ServiceImpl
 * @author: xinpei.xu
 *
 */
@Service
public class InviteRelationshipInfoServiceImpl implements InviteRelationshipInfoService {

    @Autowired
    private InviteRelationshipInfoDao inviteRelationshipInfoDao;

}
