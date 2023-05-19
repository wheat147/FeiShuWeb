package com.wheat.feishuweb.dao;

import com.wheat.feishuweb.pojo.UserAttendanceData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyMapper {
    List<UserAttendanceData> selectByDesc(String col);
}
