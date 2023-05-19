package com.wheat.feishuweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wheat.feishuweb.pojo.UserAttendanceData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @projectName: FeiShuWeb
 * @package: com.wheat.feishuweb.dao
 * @className: UserAttendanceDataMapper
 * @author: Wheat
 * @description: TODO 加 @Mapper 和 @Repository 注解 ，同时继承BaseMapper类
 * @date: 2023/1/31 10:33
 * @version: 1.0
 */

@Mapper
@Repository
public interface UserAttendanceDataMapper extends BaseMapper<UserAttendanceData> {
}
