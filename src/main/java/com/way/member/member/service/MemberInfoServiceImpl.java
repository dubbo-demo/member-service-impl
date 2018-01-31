package com.way.member.member.service;

import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.common.util.DateUtils;
import com.way.common.util.PingYinUtil;
import com.way.common.util.SensitiveInfoUtils;
import com.way.member.member.dao.MemberDao;
import com.way.member.member.dto.InviteRelationshipInfoDto;
import com.way.member.member.dto.MemberDto;
import com.way.member.member.entity.MemberInfoEntity;
import com.way.member.rewardScore.dto.RewardScoreDto;
import com.way.member.rewardScore.service.RewardScoreService;
import com.way.member.valueAdded.dto.MemberValueAddedInfoDto;
import com.way.member.valueAdded.service.MemberValueAddedInfoService;
import com.way.member.withdrawal.dto.WithdrawalInfoDto;
import com.way.member.withdrawal.service.WithdrawalInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

	@Autowired
	private MemberValueAddedInfoService memberValueAddedInfoService;

	@Autowired
	private InviteRelationshipInfoService inviteRelationshipInfoService;

	public static final double ONELEVEL_REWARDSCORE = 0.3;

	public static final double TWOLEVEL_REWARDSCORE = 0.1;

	/**
	 * 保存用户信息
	 * @param memberDto
	 */
	@Override
	public void saveMemberInfo(MemberDto memberDto) {
		memberDto.setCreateTime(new Date());
		memberDto.setModifyTime(new Date());
		MemberInfoEntity memberInfoEntity = CommonUtils.transform(memberDto, MemberInfoEntity.class);
		memberDao.insert(memberInfoEntity);
	}

	/**
	 * @Title: loadMapByMobile
	 * @Description: 根据手机号查询用户信息
	 * @return: Map<String,Object>
	 */
	@Override
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
	@Override
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
	@Override
	public void updatePassword(String invitationCode, String newPassword) {
		memberDao.updatePassword(invitationCode, newPassword);
	}

	/**
	 * 用户注册数据保存
	 * @param memberDto
	 * @param invitationCode
	 * @param nextLevelInvitationCode
	 * @return
	 */
	@Override
	@Transactional
	public void memberRegist(MemberDto memberDto, String invitationCode, String nextLevelInvitationCode){
		// 保存客户信息表
		memberDto.setMemberType("3");// 默认90天试用期
		memberDto.setTrajectoryService("3");// 默认90天试用期
		memberDto.setFenceService("3");// 默认90天试用期
		memberDto.setMemberStartTime(new Date());
		memberDto.setMemberEndTime(DateUtils.getDayEnd(DateUtils.addDays(memberDto.getMemberStartTime(), 90)));
		saveMemberInfo(memberDto);

		// 保存推荐人层级关系
		InviteRelationshipInfoDto inviteRelationshipInfoDto = new InviteRelationshipInfoDto();
		inviteRelationshipInfoDto.setInvitationCode(invitationCode);
		inviteRelationshipInfoDto.setNextLevelInvitationCode(nextLevelInvitationCode);
		inviteRelationshipInfoDto.setUnderNextLevelInvitationCode(memberDto.getInvitationCode());
		inviteRelationshipInfoDto.setCreateTime(new Date());
		inviteRelationshipInfoDto.setModifyTime(inviteRelationshipInfoDto.getCreateTime());
		inviteRelationshipInfoService.addInviteRelationshipInfo(inviteRelationshipInfoDto);

		// 保存用户增值服务信息
		MemberValueAddedInfoDto memberValueAddedInfoDto = new MemberValueAddedInfoDto();
		memberValueAddedInfoDto.setInvitationCode(memberDto.getInvitationCode());
		memberValueAddedInfoDto.setType(1);// 1:轨迹回放,2:电子围栏
		memberValueAddedInfoDto.setIsOpen(3);// 1:是,2:否,3:试用
		memberValueAddedInfoDto.setStartTime(memberDto.getMemberStartTime());
		memberValueAddedInfoDto.setEndTime(memberDto.getMemberEndTime());
		memberValueAddedInfoService.saveMemberValueAddedInfo(memberValueAddedInfoDto);
		memberValueAddedInfoDto.setType(2);// 1:轨迹回放,2:电子围栏
		memberValueAddedInfoService.saveMemberValueAddedInfo(memberValueAddedInfoDto);

		// 保存密码表
		passwordService.savePasswordInfo(memberDto);

		// 积分奖励机制
		// rewardScoreRule(memberDto.getPhoneNo(), memberDto.getInvitationCode(), 2, "邀请用户：", ONELEVEL_REWARDSCORE, TWOLEVEL_REWARDSCORE);
	}

	/**
	 * 积分奖励机制
	 * @param phoneNo
	 * @param memberDto
	 * @param rewardScoreType
	 * @param detail
	 * @param oneLevelScore
	 * @param twoLevelScore
	 * @param serviceType
	 */
	private void rewardScoreRule(String phoneNo, MemberDto memberDto, int rewardScoreType, String detail, Double oneLevelScore, Double twoLevelScore, String serviceType) {
		// 根据二级推荐人查上两级邀请码
		ServiceResult<InviteRelationshipInfoDto> inviteRelationshipInfoDto =
				inviteRelationshipInfoService.queryInviteRelationshipInfoByUnderNextLevelInvitationCode(memberDto.getInvitationCode());
		String oneLevelInvitationCode = inviteRelationshipInfoDto.getData().getNextLevelInvitationCode();
		String twoLevelInvitationCode = inviteRelationshipInfoDto.getData().getNextLevelInvitationCode();
		// 根据推荐人编号判断推荐人是否为会员
		ServiceResult<MemberDto> oneLevelMember = memberDao.getMemberInfoByInvitationCode(oneLevelInvitationCode);
		Date date = new Date();
		// 如果推荐人是会员则加积分
		// 会员类型 1:非会员,2:正式会员,3:试用期会员
		if (null != oneLevelMember.getData()
				&& (("0".equals(serviceType) && oneLevelMember.getData().getMemberType().equals("2"))
				|| ("1".equals(serviceType) && "1".equals(oneLevelMember.getData().getTrajectoryService()))
				|| ("2".equals(serviceType) && "1".equals(oneLevelMember.getData().getFenceService())))) {
			RewardScoreDto rewardScoreDto = new RewardScoreDto();
			rewardScoreDto.setInvitationCode(oneLevelMember.getData().getPhoneNo());
			rewardScoreDto.setRewardScoreType(rewardScoreType);
			rewardScoreDto.setDetailInfo(detail + SensitiveInfoUtils.maskMobilePhone(phoneNo));
			rewardScoreDto.setRewardScore(oneLevelScore);
			rewardScoreDto.setCreateTime(date);
			rewardScoreDto.setModifyTime(date);
			// 推荐人增加积分记录
			rewardScoreService.saveRewardScore(rewardScoreDto);
			// 推荐人总积分增加
			memberDao.addRewardScore(oneLevelMember.getData().getPhoneNo(), oneLevelScore, date);
		}else{
			RewardScoreDto rewardScoreDto = new RewardScoreDto();
			rewardScoreDto.setInvitationCode("18915969782");
			rewardScoreDto.setRewardScoreType(rewardScoreType);
			rewardScoreDto.setDetailInfo(detail + SensitiveInfoUtils.maskMobilePhone(phoneNo));
			rewardScoreDto.setRewardScore(oneLevelScore);
			rewardScoreDto.setCreateTime(date);
			rewardScoreDto.setModifyTime(date);
			// 推荐人增加积分记录
			rewardScoreService.saveRewardScore(rewardScoreDto);
			// 推荐人总积分增加
			memberDao.addRewardScore("18915969782", oneLevelScore, date);
		}
		// 根据推荐人父级编号判断推荐人父级是否为会员
		ServiceResult<MemberDto> twoLevelMember = memberDao.getMemberInfoByInvitationCode(twoLevelInvitationCode);
		// 如果推荐人父级是会员则加积分
		if (null != twoLevelMember.getData()
				&& (("0".equals(serviceType) &&  twoLevelMember.getData().getMemberType().equals("2"))
				|| ("1".equals(serviceType) && "1".equals(twoLevelMember.getData().getTrajectoryService()))
				|| ("2".equals(serviceType) && "1".equals(twoLevelMember.getData().getFenceService())))) {
			RewardScoreDto rewardScoreDto = new RewardScoreDto();
			// 推荐人父级增加积分记录
			rewardScoreDto.setInvitationCode(twoLevelMember.getData().getPhoneNo());
			rewardScoreDto.setRewardScoreType(rewardScoreType);
			rewardScoreDto.setDetailInfo(SensitiveInfoUtils.maskMobilePhone(oneLevelMember.getData().getPhoneNo()) + detail + SensitiveInfoUtils.maskMobilePhone(phoneNo));
			rewardScoreDto.setRewardScore(twoLevelScore);
			rewardScoreDto.setCreateTime(date);
			rewardScoreDto.setModifyTime(date);
			// 推荐人增加积分记录
			rewardScoreService.saveRewardScore(rewardScoreDto);
			// 推荐人父级总积分增加
			memberDao.addRewardScore(twoLevelMember.getData().getPhoneNo(), twoLevelScore, date);
		}else{
			RewardScoreDto rewardScoreDto = new RewardScoreDto();
			// 推荐人父级增加积分记录
			rewardScoreDto.setInvitationCode("13815893589");
			rewardScoreDto.setRewardScoreType(rewardScoreType);
			rewardScoreDto.setDetailInfo("18915969782" + detail + SensitiveInfoUtils.maskMobilePhone(phoneNo));
			rewardScoreDto.setRewardScore(twoLevelScore);
			rewardScoreDto.setCreateTime(date);
			rewardScoreDto.setModifyTime(date);
			// 推荐人增加积分记录
			rewardScoreService.saveRewardScore(rewardScoreDto);
			// 推荐人父级总积分增加
			memberDao.addRewardScore("13815893589", twoLevelScore, date);
		}
	}


	/**
	 * 根据手机号更新用户头像id
	 * @param invitationCode
	 * @param headPicId
	 */
    @Override
    public void updateHeadPicIdByInvitationCode(String invitationCode, String headPicId) {
		memberDao.updateHeadPicIdByInvitationCode(invitationCode, headPicId);
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
		if (memberInfoEntity != null) {
			MemberDto memberDto = CommonUtils.transform(memberInfoEntity, MemberDto.class);
			// 查询用户下级用户数
			memberDto.setNextLevelCount(inviteRelationshipInfoService.getNextLevelCount(memberDto.getInvitationCode()));
			// 查询用户下下级用户数
			memberDto.setUnderNextLevelCount(inviteRelationshipInfoService.getUnderNextLevelCount(memberDto.getInvitationCode()));
			return ServiceResult.newSuccess(memberDto);
		} else {
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
	 * @param memberDto
	 * @param rewardScore
	 * @param startTime
	 * @param endTime
	 * @param name
	 */
	@Override
	@Transactional
	public void buyMemberByRewardScore(String phoneNo, MemberDto memberDto, Double rewardScore, Date startTime, Date endTime, String name) {
		MemberInfoEntity entity = new MemberInfoEntity();
		Date date = new Date();
		entity.setInvitationCode(memberDto.getInvitationCode());
		entity.setRewardScore(rewardScore);
		entity.setMemberType("2");
		if(!"2".equals(memberDto.getMemberType())){
			entity.setMemberStartTime(startTime);
		}
		entity.setMemberEndTime(endTime);
		entity.setModifyTime(new Date());
		// 扣除积分并且给用户设置为会员/或者延期会员
		memberDao.minusMemberTypeInfo(entity);
		// 积分明细增加记录
		RewardScoreDto rewardScoreDto = new RewardScoreDto();
		rewardScoreDto.setInvitationCode(memberDto.getInvitationCode());
		rewardScoreDto.setRewardScoreType(1);
		rewardScoreDto.setDetailInfo("购买" + name + "会员扣除积分：" + rewardScore);
		rewardScoreDto.setRewardScore(rewardScore);
		rewardScoreDto.setCreateTime(date);
		rewardScoreDto.setModifyTime(date);
		rewardScoreService.saveRewardScore(rewardScoreDto);
		// 积分奖励机制
		rewardScoreRule(phoneNo, memberDto, 2, "邀请用户：积分购买" + name + "会员", rewardScore * ONELEVEL_REWARDSCORE, rewardScore * TWOLEVEL_REWARDSCORE, "0");
	}

	/**
	 * 积分购买增值服务
	 * @param phoneNo
	 * @param memberDto
	 * @param rewardScore
	 * @param startTime
	 * @param endTime
	 * @param name
	 * @param type
	 * @param memberValueAddedInfoDto
	 */
	@Override
	@Transactional
	public void buyValueAddedServiceByRewardScore(String phoneNo, MemberDto memberDto, Double rewardScore, Date startTime, Date endTime, String name, String type, MemberValueAddedInfoDto memberValueAddedInfoDto) {
		MemberInfoEntity entity = new MemberInfoEntity();
		Date date = new Date();
		entity.setInvitationCode(memberDto.getInvitationCode());
		entity.setRewardScore(rewardScore);
		if("1".equals(type)){
			entity.setTrajectoryService("1");
		}
		if("2".equals(type)){
			entity.setFenceService("1");
		}
		entity.setModifyTime(new Date());
		// 扣除积分并且给用户设置为会员/或者延期会员
		memberDao.minusMemberTypeInfo(entity);
		if(null == memberValueAddedInfoDto){
			memberValueAddedInfoDto = new MemberValueAddedInfoDto();
			memberValueAddedInfoDto.setInvitationCode(memberDto.getInvitationCode());
			memberValueAddedInfoDto.setType(Integer.valueOf(type));
			memberValueAddedInfoDto.setStartTime(startTime);
			memberValueAddedInfoDto.setEndTime(endTime);
			memberValueAddedInfoDto.setIsOpen(1);
			// 新增用户增值服务信息
			memberValueAddedInfoService.saveMemberValueAddedInfo(memberValueAddedInfoDto);
		}else{
			memberValueAddedInfoDto.setInvitationCode(memberDto.getInvitationCode());
			memberValueAddedInfoDto.setType(Integer.valueOf(type));
			if("2".equals(memberDto.getTrajectoryService()) || "2".equals(memberDto.getFenceService())){
				memberValueAddedInfoDto.setStartTime(startTime);
			}
			memberValueAddedInfoDto.setEndTime(endTime);
			memberValueAddedInfoDto.setIsOpen(1);
			// 更新用户增值服务信息
			memberValueAddedInfoService.updateMemberValueAddedInfo(memberValueAddedInfoDto);
		}
		// 积分明细增加记录
		RewardScoreDto rewardScoreDto = new RewardScoreDto();
		rewardScoreDto.setInvitationCode(memberDto.getInvitationCode());
		if("1".equals(type)){
			rewardScoreDto.setRewardScoreType(6);
		}
		if("2".equals(type)){
			rewardScoreDto.setRewardScoreType(7);
		}

		rewardScoreDto.setDetailInfo("购买" + name + "扣除积分：" + rewardScore);
		rewardScoreDto.setRewardScore(rewardScore);
		rewardScoreDto.setCreateTime(date);
		rewardScoreDto.setModifyTime(date);
		rewardScoreService.saveRewardScore(rewardScoreDto);
		// 积分奖励机制
		rewardScoreRule(phoneNo, memberDto, 2, "邀请用户：积分购买" + name, rewardScore * ONELEVEL_REWARDSCORE, rewardScore * TWOLEVEL_REWARDSCORE, type);
	}

	/**
	 * 积分转增
	 * @param rewardScore
	 * @param phoneNo
	 * @param friendPhoneNo
	 */
	@Override
	@Transactional
	public void transferRewardScoreToFriend(String invitationCode, Double rewardScore, String friendInvitationCode, String phoneNo, String friendPhoneNo) {
		RewardScoreDto rewardScoreDto = new RewardScoreDto();
		MemberInfoEntity entity = new MemberInfoEntity();
		Date date = new Date();
		entity.setInvitationCode(invitationCode);
		entity.setRewardScore(rewardScore);
		entity.setModifyTime(date);
		// 删除自己积分
		memberDao.minusMemberTypeInfo(entity);
		// 积分明细表增加记录
		rewardScoreDto.setInvitationCode(invitationCode);
		rewardScoreDto.setRewardScoreType(4);
		rewardScoreDto.setDetailInfo("转赠用户：" + SensitiveInfoUtils.maskMobilePhone(friendPhoneNo));
		rewardScoreDto.setRewardScore(rewardScore);
		rewardScoreDto.setCreateTime(date);
		rewardScoreDto.setModifyTime(date);
		rewardScoreService.saveRewardScore(rewardScoreDto);
		// 增加转赠用户积分
		memberDao.addRewardScore(friendPhoneNo, rewardScore, date);
		// 积分明细表增加记录
		rewardScoreDto.setInvitationCode(friendInvitationCode);
		rewardScoreDto.setRewardScoreType(5);
		rewardScoreDto.setDetailInfo(SensitiveInfoUtils.maskMobilePhone(phoneNo) + "用户转赠");
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
		withdrawalInfoDto.setStatus(0);
		Date date = new Date();
		withdrawalInfoService.withdrawalRewardScore(withdrawalInfoDto);
		// 用户扣除积分
		MemberInfoEntity entity = new MemberInfoEntity();
		entity.setInvitationCode(withdrawalInfoDto.getInvitationCode());
		entity.setRewardScore(withdrawalInfoDto.getRewardScore());
		memberDao.minusMemberTypeInfo(entity);


		// 积分明细表增加记录
		RewardScoreDto rewardScoreDto = new RewardScoreDto();
		rewardScoreDto.setInvitationCode(withdrawalInfoDto.getInvitationCode());
		rewardScoreDto.setRewardScoreType(3);
		rewardScoreDto.setDetailInfo("用户提现" + withdrawalInfoDto.getRewardScore() + "积分，到银行卡：" + withdrawalInfoDto.getBankNumber());
		rewardScoreDto.setRewardScore(withdrawalInfoDto.getRewardScore());
		rewardScoreDto.setCreateTime(date);
		rewardScoreDto.setModifyTime(date);
		rewardScoreService.saveRewardScore(rewardScoreDto);
	}

	/**
	 * 查询总页数
	 * @param invitationCode
	 * @return
	 */
	@Override
	public Integer getWithdrawalRewardScoreCount(String invitationCode) {
		return withdrawalInfoService.getWithdrawalRewardScoreCount(invitationCode);
	}

	/**
	 * 获取积分提现记录
	 * @param invitationCode
	 * @param pageNumber
	 * @return
	 */
	@Override
	public List<WithdrawalInfoDto> getWithdrawalRewardScoreInfo(String invitationCode, int pageNumber) {
		return withdrawalInfoService.getWithdrawalRewardScoreInfo(invitationCode, pageNumber);
	}

	/**
	 * 充值购买会员/增值服务
	 * @param phoneNo
	 * @param type
	 * @param memberDto
	 * @param amount
	 * @param startTime
	 * @param endTime
	 * @param name
	 */
	@Override
	@Transactional
	public void buyServiceByRecharge(String phoneNo, String type, MemberDto memberDto, Double amount, Date startTime, Date endTime, String name) {
		MemberInfoEntity entity = new MemberInfoEntity();
		entity.setInvitationCode(memberDto.getInvitationCode());
		if("0".equals(type)){
			entity.setMemberType("2");
		}
		if("1".equals(type)){
			entity.setTrajectoryService("1");
		}
		if("2".equals(type)){
			entity.setFenceService("1");
		}
		if(!"2".equals(memberDto.getMemberType())){
			entity.setMemberStartTime(startTime);
		}
		entity.setMemberEndTime(endTime);
		entity.setModifyTime(new Date());
		// 给用户设置为会员/或者延期会员
		memberDao.minusMemberTypeInfo(entity);
		if("1".equals(type) || "2".equals(type)){
			// 根据增值服务类型获取用户增值服务信息
			MemberValueAddedInfoDto memberValueAddedInfoDto = memberValueAddedInfoService.getMemberValueAddedInfoByType(phoneNo, type);
			if(null == memberValueAddedInfoDto){
				memberValueAddedInfoDto = new MemberValueAddedInfoDto();
				memberValueAddedInfoDto.setInvitationCode(memberDto.getInvitationCode());
				memberValueAddedInfoDto.setType(Integer.valueOf(type));
				memberValueAddedInfoDto.setStartTime(startTime);
				memberValueAddedInfoDto.setEndTime(endTime);
				memberValueAddedInfoDto.setIsOpen(1);
				// 新增用户增值服务信息
				memberValueAddedInfoService.saveMemberValueAddedInfo(memberValueAddedInfoDto);
			}else{
				memberValueAddedInfoDto.setInvitationCode(memberDto.getInvitationCode());
				memberValueAddedInfoDto.setType(Integer.valueOf(type));
				if("2".equals(memberDto.getTrajectoryService()) || "2".equals(memberDto.getFenceService())){
					memberValueAddedInfoDto.setStartTime(startTime);
				}
				memberValueAddedInfoDto.setEndTime(endTime);
				memberValueAddedInfoDto.setIsOpen(1);
				// 更新用户增值服务信息
				memberValueAddedInfoService.updateMemberValueAddedInfo(memberValueAddedInfoDto);
			}
		}
		// 积分奖励机制
		rewardScoreRule(phoneNo, memberDto, 2, "邀请用户：", ONELEVEL_REWARDSCORE, TWOLEVEL_REWARDSCORE, type);
	}

	/**
	 * 根据邀请码查邀请人信息
	 * @param invitationCode
	 * @return
	 */
	@Override
	public ServiceResult<MemberDto> loadMapByInvitationCode(String invitationCode) {
		MemberInfoEntity memberInfoEntity = memberDao.loadMapByInvitationCode(invitationCode);
		if(memberInfoEntity != null){
			MemberDto memberDto = CommonUtils.transform(memberInfoEntity, MemberDto.class);
			return ServiceResult.newSuccess(memberDto);
		}else{
			return ServiceResult.newSuccess(null);
		}
	}

}
