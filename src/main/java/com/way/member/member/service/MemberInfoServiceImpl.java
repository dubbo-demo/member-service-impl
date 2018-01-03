package com.way.member.member.service;

import com.way.common.result.ServiceResult;
import com.way.common.util.CommonUtils;
import com.way.common.util.PingYinUtil;
import com.way.member.member.dao.MemberDao;
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
	public void updatePassword(String phoneNo, String newPassword) {
		memberDao.updatePassword(phoneNo, newPassword);
	}

	/**
	 * 用户注册数据保存
	 * @param memberDto
	 * @return
	 */
	@Override
	@Transactional
	public void memberRegist(MemberDto memberDto){
		memberDto.setMemberType("1");
		memberDto.setTrajectoryService("2");
		memberDto.setFenceService("2");
		// 保存客户信息表
		saveMemberInfo(memberDto);
		// 保存密码表
		passwordService.savePasswordInfo(memberDto);
		// 积分奖励机制
		// rewardScoreRule(memberDto.getPhoneNo(), memberDto.getInvitationCode(), 2, "邀请用户：", ONELEVEL_REWARDSCORE, TWOLEVEL_REWARDSCORE);
	}

	/**
	 * 积分奖励机制
	 * @param phoneNo
	 * @param invitationCode
	 * @param type
	 * @param detail
	 * @param oneLevelScore
	 * @param twoLevelScore
	 */
	private void rewardScoreRule(String phoneNo, String invitationCode, int type, String detail, Double oneLevelScore, Double twoLevelScore) {
		// 根据推荐人手机号判断推荐人是否为会员
		ServiceResult<MemberDto> oneLevelMember = loadMapByMobile(invitationCode);
		Date date = new Date();
		// 如果推荐人是会员则加积分
		// 会员类型 1:非会员,2:正式会员,3:试用期会员
		if (null != oneLevelMember.getData() && oneLevelMember.getData().getMemberType().equals("2")) {
			RewardScoreDto rewardScoreDto = new RewardScoreDto();
			rewardScoreDto.setPhoneNo(oneLevelMember.getData().getPhoneNo());
			rewardScoreDto.setRewardScoreType(type);
			rewardScoreDto.setDetailInfo(detail + phoneNo);
			rewardScoreDto.setRewardScore(oneLevelScore);
			rewardScoreDto.setCreateTime(date);
			rewardScoreDto.setModifyTime(date);
			// 推荐人增加积分记录
			rewardScoreService.saveRewardScore(rewardScoreDto);
			// 推荐人总积分增加
			memberDao.addRewardScore(oneLevelMember.getData().getPhoneNo(), oneLevelScore, date);
		}
		// 根据推荐人父级手机号判断推荐人父级是否为会员
		ServiceResult<MemberDto> twoLevelMember = loadMapByMobile(oneLevelMember.getData().getInvitationCode());
		// 如果推荐人父级是会员则加积分
		if (null != twoLevelMember.getData() && twoLevelMember.getData().getMemberType().equals("2")) {
			RewardScoreDto rewardScoreDto = new RewardScoreDto();
			// 推荐人父级增加积分记录
			rewardScoreDto.setPhoneNo(twoLevelMember.getData().getPhoneNo());
			rewardScoreDto.setRewardScoreType(type);
			rewardScoreDto.setDetailInfo(oneLevelMember.getData().getPhoneNo() + detail + phoneNo);
			rewardScoreDto.setRewardScore(twoLevelScore);
			rewardScoreDto.setCreateTime(date);
			rewardScoreDto.setModifyTime(date);
			// 推荐人增加积分记录
			rewardScoreService.saveRewardScore(rewardScoreDto);
			// 推荐人父级总积分增加
			memberDao.addRewardScore(twoLevelMember.getData().getPhoneNo(), twoLevelScore, date);
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
		entity.setPhoneNo(phoneNo);
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
		rewardScoreDto.setPhoneNo(phoneNo);
		rewardScoreDto.setRewardScoreType(1);
		rewardScoreDto.setDetailInfo("购买" + name + "会员扣除积分：" + rewardScore);
		rewardScoreDto.setRewardScore(rewardScore);
		rewardScoreDto.setCreateTime(date);
		rewardScoreDto.setModifyTime(date);
		rewardScoreService.saveRewardScore(rewardScoreDto);
		// 积分奖励机制
		rewardScoreRule(phoneNo, memberDto.getInvitationCode(), 2, "邀请用户：积分购买" + name + "会员", rewardScore * ONELEVEL_REWARDSCORE, rewardScore * TWOLEVEL_REWARDSCORE);
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
		entity.setPhoneNo(phoneNo);
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
			memberValueAddedInfoDto.setPhoneNo(phoneNo);
			memberValueAddedInfoDto.setType(Integer.valueOf(type));
			memberValueAddedInfoDto.setStartTime(startTime);
			memberValueAddedInfoDto.setEndTime(endTime);
			memberValueAddedInfoDto.setIsOpen(1);
			// 新增用户增值服务信息
			memberValueAddedInfoService.saveMemberValueAddedInfo(memberValueAddedInfoDto);
		}else{
			memberValueAddedInfoDto.setPhoneNo(phoneNo);
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
		rewardScoreDto.setPhoneNo(phoneNo);
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
		rewardScoreRule(phoneNo, memberDto.getInvitationCode(), 2, "邀请用户：积分购买" + name, rewardScore * ONELEVEL_REWARDSCORE, rewardScore * TWOLEVEL_REWARDSCORE);
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
		Date date = new Date();
		entity.setPhoneNo(phoneNo);
		entity.setRewardScore(rewardScore);
		entity.setModifyTime(date);
		// 删除自己积分
		memberDao.minusMemberTypeInfo(entity);
		// 积分明细表增加记录
		rewardScoreDto.setPhoneNo(phoneNo);
		rewardScoreDto.setRewardScoreType(4);
		rewardScoreDto.setDetailInfo("转赠用户：" + friendPhoneNo);
		rewardScoreDto.setRewardScore(rewardScore);
		rewardScoreDto.setCreateTime(date);
		rewardScoreDto.setModifyTime(date);
		rewardScoreService.saveRewardScore(rewardScoreDto);
		// 增加转赠用户积分
		memberDao.addRewardScore(friendPhoneNo, rewardScore, date);
		// 积分明细表增加记录
		rewardScoreDto.setPhoneNo(friendPhoneNo);
		rewardScoreDto.setRewardScoreType(5);
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
		Date date = new Date();
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
		rewardScoreDto.setCreateTime(date);
		rewardScoreDto.setModifyTime(date);
		rewardScoreService.saveRewardScore(rewardScoreDto);
	}

	/**
	 * 查询总页数
	 * @param phoneNo
	 * @return
	 */
	@Override
	public Integer getWithdrawalRewardScoreCount(String phoneNo) {
		return withdrawalInfoService.getWithdrawalRewardScoreCount(phoneNo);
	}

	/**
	 * 获取积分提现记录
	 * @param phoneNo
	 * @param pageNumber
	 * @return
	 */
	@Override
	public List<WithdrawalInfoDto> getWithdrawalRewardScoreInfo(String phoneNo, int pageNumber) {
		return withdrawalInfoService.getWithdrawalRewardScoreInfo(phoneNo, pageNumber);
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
		entity.setPhoneNo(phoneNo);
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
				memberValueAddedInfoDto.setPhoneNo(phoneNo);
				memberValueAddedInfoDto.setType(Integer.valueOf(type));
				memberValueAddedInfoDto.setStartTime(startTime);
				memberValueAddedInfoDto.setEndTime(endTime);
				memberValueAddedInfoDto.setIsOpen(1);
				// 新增用户增值服务信息
				memberValueAddedInfoService.saveMemberValueAddedInfo(memberValueAddedInfoDto);
			}else{
				memberValueAddedInfoDto.setPhoneNo(phoneNo);
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
		rewardScoreRule(phoneNo, memberDto.getInvitationCode(), 2, "邀请用户：", ONELEVEL_REWARDSCORE, TWOLEVEL_REWARDSCORE);
	}

}
