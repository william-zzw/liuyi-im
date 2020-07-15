package com.creolophus.liuyi.dao;

import com.creolophus.liuyi.common.entity.User;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Repository
public interface UserDao extends BaseMapper<User> {

}