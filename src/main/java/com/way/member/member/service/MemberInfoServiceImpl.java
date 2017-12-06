package com.way.member.member.service;

import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.common.util.DateUtils;
import com.way.common.util.PingYinUtil;
import com.way.member.member.dao.MemberDao;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.entity.MemberInfoEntity;
import com.way.member.rewardScore.dto.RewardScoreDto;
import com.way.member.rewardScore.service.RewardScoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName: MemberServiceImpl
 * @Description: 会员ServiceImpl
 * @author: xinpei.xu
 * @date: 2017/08/20 21:53
 *
 */
@Service
public class MemberInfoServiceImpl implements MemberInfoService {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private RewardScoreService rewardScoreService;

	public static final Integer ONELEVEL_REWARDSCORE = 20;

	public static final Integer TWOLEVEL_REWARDSCORE = 10;

	/**
	 * 保存用户信息
	 * @param memberDto
	 */
	public void saveMemberInfo(MemberDto memberDto) {
		MemberInfoEntity memberInfoEntity = CommonUtils.transform(memberDto, MemberInfoEntity.class);
		memberDao.insert(memberInfoEntity);
	}

	/**
	 * @Title: loadMapByMobile
	 * @Description: 根据手机号查询用户信息
	 * @return: Map<String,Object>
	 */
	public ServiceResult<MemberDto> loadMapByMobile(String phoneNo) {
		MemberInfoEntity memberInfoEntity = memberDao.selectUserInfoByMobile(phoneNo);
		if(memberInfoEntity != null){
			MemberDto memberDto = CommonUtils.transform(memberInfoEntity, MemberDto.class);
			return ServiceResult.newSuccess(memberDto);
		}else{
			return ServiceResult.newSuccess(null);
		}
	}

	/**
	 * @Title: queryMemberInfo
	 * @Description: 根据手机号查询会员信息
	 * @return: Map<String, String>
	 */
	public ServiceResult<MemberDto> queryMemberInfo(String phoneNo) {
		MemberInfoEntity memberInfoEntity = memberDao.queryMemberInfo(phoneNo);
		if(memberInfoEntity != null){
			MemberDto memberDto = CommonUtils.transform(memberInfoEntity, MemberDto.class);
			return ServiceResult.newSuccess(memberDto);
		}else{
			return ServiceResult.newSuccess(null);
		}
	}

	/**
	 * @Title: updatePassword
	 * @Description: 更新密码
	 * @return: void
	 */
	public void updatePassword(String phoneNo, String newPassword) {
		memberDao.updatePassword(phoneNo, newPassword);
	}

	/**
	 * 用户注册数据保存
	 * @param memberDto
	 * @return
	 */
	@Transactional
	public void memberRegist(MemberDto memberDto){
		memberDto.setMemberType("2");
		memberDto.setValueAddedService("1");
		memberDto.setMemberStartTime(new Date());
		memberDto.setMemberEndTime(DateUtils.addDays(memberDto.getMemberStartTime(), 30));
		memberDto.setValueAddedServiceStartTime(memberDto.getMemberStartTime());
		memberDto.setValueAddedServiceEndTime(memberDto.getMemberEndTime());
		// 保存客户信息表
		saveMemberInfo(memberDto);
		// 保存密码表
		passwordService.savePasswordInfo(memberDto);
		// 根据推荐人手机号判断推荐人是否为会员
		ServiceResult<MemberDto> oneLevelMember = loadMapByMobile(memberDto.getInvitationCode());
		// 如果推荐人是会员则加积分
		// 会员类型 1:非会员,2:正式会员,3:试用期会员
		if (oneLevelMember.getData().getMemberType().equals("2")) {
			RewardScoreDto rewardScoreDto = new RewardScoreDto();
			rewardScoreDto.setPhoneNo(oneLevelMember.getData().getPhoneNo());
			rewardScoreDto.setRewardScoreType(2);
			rewardScoreDto.setDetailInfo("邀请用户：" + memberDto.getPhoneNo());
			rewardScoreDto.setRewardScore(ONELEVEL_REWARDSCORE);
			// 推荐人增加积分记录
			rewardScoreService.saveRewardScore(rewardScoreDto);
			// 推荐人总积分增加
			memberDao.updateRewardScore(oneLevelMember.getData().getPhoneNo(), TWOLEVEL_REWARDSCORE);
			// 根据推荐人父级手机号判断推荐人父级是否为会员
			ServiceResult<MemberDto> twoLevelMember = loadMapByMobile(oneLevelMember.getData().getInvitationCode());
			// 如果推荐人父级是会员则加积分
			if (twoLevelMember.getData().getMemberType().equals("2")) {
				// 推荐人父级增加积分记录
				rewardScoreDto.setPhoneNo(twoLevelMember.getData().getPhoneNo());
				rewardScoreDto.setRewardScoreType(2);
				rewardScoreDto.setDetailInfo(oneLevelMember.getData().getPhoneNo() + "邀请用户：" + memberDto.getPhoneNo());
				rewardScoreDto.setRewardScore(TWOLEVEL_REWARDSCORE);
				// 推荐人增加积分记录
				rewardScoreService.saveRewardScore(rewardScoreDto);
				// 推荐人父级总积分增加
				memberDao.updateRewardScore(twoLevelMember.getData().getPhoneNo(), TWOLEVEL_REWARDSCORE);
			}
		}
	}

	/**
	 * 根据手机号更新用户头像id
	 * @param phoneNo
	 * @param headPicId
	 */
    @Override
    public void updateHeadPicIdByPhoneNo(String phoneNo, String headPicId) {
		memberDao.updateHeadPicIdByPhoneNo(phoneNo, headPicId);
    }

	/**
	 * 根据手机号搜索用户
	 * @param phoneNo
	 * @return
	 */
	@Override
	public ServiceResult<MemberDto> searchUserByPhoneNo(String phoneNo) {
		MemberInfoEntity memberInfoEntity = memberDao.searchUserByPhoneNo(phoneNo);
		if(memberInfoEntity != null){
			MemberDto memberDto = CommonUtils.transform(memberInfoEntity, MemberDto.class);
			return ServiceResult.newSuccess(memberDto);
		}else{
			return ServiceResult.newSuccess(null);
		}
	}

	/**
	 * 查看个人信息
	 * @param phoneNo
	 * @return
	 */
	@Override
	public ServiceResult<MemberDto> getMemberInfo(String phoneNo) {
		MemberInfoEntity memberInfoEntity = memberDao.getMemberInfo(phoneNo);
		if(memberInfoEntity != null){
			MemberDto memberDto = CommonUtils.transform(memberInfoEntity, MemberDto.class);
			return ServiceResult.newSuccess(memberDto);
		}else{
			return ServiceResult.newSuccess(null);
		}
	}

	/**
	 * 修改个人信息
	 * @param dto
	 * @return
	 */
	@Override
	public ServiceResult<Object> modifyMemberInfo(MemberDto dto) {
		if(StringUtils.isNotBlank(dto.getNickName())){
			dto.setNickSpell(PingYinUtil.getPingYin(dto.getNickName()));
		}
		dto.setModifyTime(new Date());
		memberDao.modifyMemberInfo(dto);
		return ServiceResult.newSuccess();
	}

}
