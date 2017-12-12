package com.way.member.member.service;

import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.common.util.DateUtils;
import com.way.common.util.PingYinUtil;
import com.way.member.withdrawal.dto.WithdrawalInfoDto;
import com.way.member.withdrawal.service.WithdrawalInfoService;
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

	@Autowired
	private WithdrawalInfoService withdrawalInfoService;

	public static final Double ONELEVEL_REWARDSCORE = 20.0;

	public static final Double TWOLEVEL_REWARDSCORE = 10.0;

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
		memberDto.setValueAddedService("2");
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
			memberDao.addRewardScore(oneLevelMember.getData().getPhoneNo(), ONELEVEL_REWARDSCORE);
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
				memberDao.addRewardScore(twoLevelMember.getData().getPhoneNo(), TWOLEVEL_REWARDSCORE);
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

	/**
	 * 积分购买会员
	 * @param phoneNo
	 * @param rewardScore
	 * @param startTime
	 * @param endTime
	 * @param name
	 */
	@Override
	@Transactional
	public void buyMemberByRewardScore(String phoneNo, Double rewardScore, Date startTime, Date endTime, String name) {
		MemberInfoEntity entity = new MemberInfoEntity();
		entity.setPhoneNo(phoneNo);
		entity.setRewardScore(rewardScore);
		entity.setMemberType("2");
		entity.setValueAddedServiceStartTime(startTime);
		entity.setValueAddedServiceEndTime(endTime);
		// 扣除积分并且给用户设置为会员/或者延期会员
		memberDao.minusMemberTypeInfo(entity);
		// 积分明细增加记录
		RewardScoreDto rewardScoreDto = new RewardScoreDto();
		rewardScoreDto.setPhoneNo(phoneNo);
		rewardScoreDto.setRewardScoreType(1);
		rewardScoreDto.setDetailInfo("购买" + name + "会员扣除积分：" + rewardScore);
		rewardScoreDto.setRewardScore(rewardScore);
		rewardScoreService.saveRewardScore(rewardScoreDto);
	}

	/**
	 * 积分购买增值服务
	 * @param phoneNo
	 * @param rewardScore
	 * @param startTime
	 * @param endTime
	 * @param name
	 */
	@Override
	@Transactional
	public void buyValueAddedServiceByRewardScore(String phoneNo, Double rewardScore, Date startTime, Date endTime, String name) {
		MemberInfoEntity entity = new MemberInfoEntity();
		entity.setPhoneNo(phoneNo);
		entity.setRewardScore(rewardScore);
		entity.setValueAddedService("1");
		entity.setValueAddedServiceStartTime(startTime);
		entity.setValueAddedServiceEndTime(endTime);
		// 扣除积分并且给用户设置为会员/或者延期会员
		memberDao.minusMemberTypeInfo(entity);
		// 积分明细增加记录
		RewardScoreDto rewardScoreDto = new RewardScoreDto();
		rewardScoreDto.setPhoneNo(phoneNo);
		rewardScoreDto.setRewardScoreType(5);
		rewardScoreDto.setDetailInfo("购买" + name + "增值服务扣除积分：" + rewardScore);
		rewardScoreDto.setRewardScore(rewardScore);
		rewardScoreService.saveRewardScore(rewardScoreDto);
		//
	}

	/**
	 * 积分转增
	 * @param phoneNo
	 * @param rewardScore
	 * @param friendPhoneNo
	 */
	@Override
	@Transactional
	public void transferRewardScoreToFriend(String phoneNo, Double rewardScore, String friendPhoneNo) {
		RewardScoreDto rewardScoreDto = new RewardScoreDto();
		MemberInfoEntity entity = new MemberInfoEntity();
		entity.setPhoneNo(phoneNo);
		entity.setRewardScore(rewardScore);
		// 删除自己积分
		memberDao.minusMemberTypeInfo(entity);
		// 积分明细表增加记录
		rewardScoreDto.setPhoneNo(phoneNo);
		rewardScoreDto.setRewardScoreType(2);
		rewardScoreDto.setDetailInfo("用户转赠：" + friendPhoneNo);
		rewardScoreDto.setRewardScore(rewardScore);
		rewardScoreService.saveRewardScore(rewardScoreDto);
		// 增加转赠用户积分
		memberDao.addRewardScore(friendPhoneNo, rewardScore);
		// 积分明细表增加记录
		rewardScoreDto.setPhoneNo(friendPhoneNo);
		rewardScoreDto.setRewardScoreType(2);
		rewardScoreDto.setDetailInfo(phoneNo + "用户转赠");
		rewardScoreDto.setRewardScore(rewardScore);
		rewardScoreService.saveRewardScore(rewardScoreDto);
	}

	/**
	 * 积分提现
	 * @param withdrawalInfoDto
	 */
	@Override
	@Transactional
	public void withdrawalRewardScore(WithdrawalInfoDto withdrawalInfoDto) {
		// 增加积分提现记录
		withdrawalInfoDto.setStatus(1);
		withdrawalInfoService.withdrawalRewardScore(withdrawalInfoDto);
		// 用户扣除积分
		MemberInfoEntity entity = new MemberInfoEntity();
		entity.setPhoneNo(withdrawalInfoDto.getPhoneNo());
		entity.setRewardScore(withdrawalInfoDto.getRewardScore());
		memberDao.minusMemberTypeInfo(entity);

		// 积分明细表增加记录
		RewardScoreDto rewardScoreDto = new RewardScoreDto();
		rewardScoreDto.setPhoneNo(withdrawalInfoDto.getPhoneNo());
		rewardScoreDto.setRewardScoreType(3);
		rewardScoreDto.setDetailInfo("用户提现" + withdrawalInfoDto.getRewardScore() + "积分，到银行卡：" + withdrawalInfoDto.getBankNumber());
		rewardScoreDto.setRewardScore(withdrawalInfoDto.getRewardScore());
		rewardScoreService.saveRewardScore(rewardScoreDto);
	}

}
