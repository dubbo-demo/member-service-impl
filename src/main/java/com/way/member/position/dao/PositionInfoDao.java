package com.way.member.position.dao;

import com.way.common.result.ServiceResult;
import com.way.common.rom.IBaseMapper;
import com.way.member.member.entity.PositionInfoPo;
import com.way.member.position.dto.PositionInfoDto;
import org.apache.ibatis.annotations.Param;

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
     * @param flag
     * @param id
     */
    void updatePosition(@Param("po") PositionInfoPo po, @Param("flag") String flag, @Param("id") Long id);

    /**
     * 根据手机号获取用户实时坐标
     * @param phoneNo
     * @param flag
     * @return
     */
    ServiceResult<PositionInfoDto> getRealtimePositionByPhoneNo(@Param("phoneNo") String phoneNo, @Param("flag") String flag);
}
