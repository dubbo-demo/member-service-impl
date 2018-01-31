package com.way.member.position.service;

import com.way.common.constant.NumberConstants;
import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.entity.PositionInfoPo;
import com.way.member.member.service.MemberInfoService;
import com.way.member.position.dao.PositionInfoDao;
import com.way.member.position.dto.PositionInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述：定位信息ServiceImpl
 *
 * @Author：xinpei.xu
 * @Date：2017/08/28 21:12
 */
@Service
public class PositionInfoServiceImpl implements PositionInfoService {

    @Autowired
    private PositionInfoDao positionInfoDao;

    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 上传坐标
     * @param positionInfoDto
     * @param flag
     */
    @Override
    public void savePosition(PositionInfoDto positionInfoDto, String flag) {
        if(null == flag){
            // 分20张表
            flag = subTable(positionInfoDto.getPhoneNo());
        }
        // 根据手机号查用户邀请码
        ServiceResult<MemberDto> memberDto = memberInfoService.getMemberInfo(positionInfoDto.getPhoneNo());
        String invitationCode = memberDto.getData().getInvitationCode();
        positionInfoDto.setInvitationCode(invitationCode);
        PositionInfoPo positionInfoPo = CommonUtils.transform(positionInfoDto, PositionInfoPo.class);
        positionInfoDao.savePosition(positionInfoPo, flag);
    }

    /**
     * 更新用户坐标
     * @param positionInfoDto
     * @param id
     */
    @Override
    public void updatePosition(PositionInfoDto positionInfoDto, Long id) {
        PositionInfoPo positionInfoPo = CommonUtils.transform(positionInfoDto, PositionInfoPo.class);
        positionInfoDao.updatePosition(positionInfoPo, id);
    }

    /**
     * 根据手机号获取用户实时坐标
     *
     * @param phoneNo
     * @param modifyTime
     * @return
     */
    @Override
    public ServiceResult<PositionInfoDto> getRealTimePositionByPhoneNo(String phoneNo, String modifyTime) {
        // 根据手机号查用户邀请码
        ServiceResult<MemberDto> memberDto = memberInfoService.getMemberInfo(phoneNo);
        String invitationCode = memberDto.getData().getInvitationCode();

        ServiceResult<PositionInfoDto> serviceResult = ServiceResult.newSuccess();
        PositionInfoDto positionInfoDto = positionInfoDao.getRealTimePositionByInvitationCode(invitationCode, modifyTime);
        positionInfoDto.setPhoneNo(phoneNo);
        serviceResult.setData(positionInfoDto);
        return serviceResult;
    }

    /**
     * 查询用户历史轨迹坐标
     * @param phoneNo
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<PositionInfoDto> getMemberHistoryPositions(String phoneNo, String startTime, String endTime) {
        // 分20张表
        String flag = subTable(phoneNo);
        return positionInfoDao.getMemberHistoryPositions(phoneNo, flag, startTime, endTime);
    }

    /**
     * 分表算法
     */
    private String subTable(String phoneNo) {
        return String.valueOf(Long.valueOf(phoneNo) % NumberConstants.NUM_20 + NumberConstants.NUM_ONE);
    }
}
