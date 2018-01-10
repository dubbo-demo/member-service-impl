package com.way.member.position.dao;

import com.way.common.rom.IBaseMapper;
import com.way.member.member.entity.PositionInfoPo;
import com.way.member.position.dto.PositionInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述：定位信息Dao
 *
 * @Author：xinpei.xu
 * @Date：2017/08/28 21:19
 */
public interface PositionInfoDao extends IBaseMapper {

    /**
     * 上传坐标
     * @param po
     * @param flag
     */
    void savePosition(@Param("po") PositionInfoPo po, @Param("flag") String flag);

    /**
     * 更新用户坐标
     * @param po
     * @param id
     */
    void updatePosition(@Param("po") PositionInfoPo po, @Param("id") Long id);

    /**
     * 根据手机号获取用户实时坐标
     * @param phoneNo
     * @param modifyTime
     * @return
     */
    PositionInfoDto getRealtimePositionByPhoneNo(@Param("phoneNo") String phoneNo, @Param("modifyTime") String modifyTime);

    /**
     * 查询用户历史轨迹坐标
     * @param phoneNo
     * @param flag
     * @param startTime
     *@param endTime @return
     */
    List<PositionInfoDto> getMemberHistoryPositions(@Param("phoneNo") String phoneNo, @Param("flag") String flag,
                                                    @Param("startTime") String startTime, @Param("endTime") String endTime);
}
