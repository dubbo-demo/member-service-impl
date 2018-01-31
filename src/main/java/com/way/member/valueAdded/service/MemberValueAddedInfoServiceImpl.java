package com.way.member.valueAdded.service;

import com.way.common.util.CommonUtils;
import com.way.member.valueAdded.dao.MemberValueAddedInfoDao;
import com.way.member.valueAdded.dto.MemberValueAddedInfoDto;
import com.way.member.valueAdded.entity.MemberValueAddedInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户增值服务信息ServiceImpl
 * @author: xinpei.xu
 */
@Service
public class MemberValueAddedInfoServiceImpl implements MemberValueAddedInfoService {

    @Autowired
    private MemberValueAddedInfoDao memberValueAddedInfoDao;

    /**
     * 根据增值服务类型获取用户增值服务信息
     *
     * @param invitationCode
     * @param type
     * @return
     */
    @Override
    public MemberValueAddedInfoDto getMemberValueAddedInfoByType(String invitationCode, String type) {
        MemberValueAddedInfoEntity entity = memberValueAddedInfoDao.getMemberValueAddedInfoByType(invitationCode, type);
        return CommonUtils.transform(entity, MemberValueAddedInfoDto.class);
    }

    /**
     * 新增用户增值服务信息
     * @param memberValueAddedInfoDto
     */
    @Override
    public void saveMemberValueAddedInfo(MemberValueAddedInfoDto memberValueAddedInfoDto) {
        MemberValueAddedInfoEntity entity = CommonUtils.transform(memberValueAddedInfoDto, MemberValueAddedInfoEntity.class);
        memberValueAddedInfoDao.saveMemberValueAddedInfo(entity);
    }

    /**
     * 更新用户增值服务信息
     * @param memberValueAddedInfoDto
     */
    @Override
    public void updateMemberValueAddedInfo(MemberValueAddedInfoDto memberValueAddedInfoDto) {
        MemberValueAddedInfoEntity entity = CommonUtils.transform(memberValueAddedInfoDto, MemberValueAddedInfoEntity.class);
        memberValueAddedInfoDao.updateMemberValueAddedInfo(entity);
    }
}
