package com.wheat.feishuweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wheat.feishuweb.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @projectName: FeiShuWeb
 * @package: com.wheat.feishuweb.dao
 * @className: UserMapper
 * @author: Wheat
 * @description: TODO
 * @date: 2023/1/30 17:33
 * @version: 1.0
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}
