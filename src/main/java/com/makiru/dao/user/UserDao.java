package com.makiru.dao.user;

import com.makiru.pojo.Role;
import com.makiru.pojo.User;

import java.util.List;

public interface UserDao {
    //根据用户名找用户
    User getUserByName(String userName);
    //根据用户id找用户
    User getUserById(String id);
    //更新用户信息
    int updateUser(User user);
    //修改密码
    int updatePwd(String userId, String newPwd);
    //插入用户
    int insertUser(User user);
    //获取所有的角色
    List<Role> getRoleList();
    //根据条件获取用户
    List<User> getUserList(String userName, int userRole, int gender, int pageSize, int pageIndex);
    //获取用户数量
    int getUserCount(String userName, int userRole, int gender);
    //删除用户（普通用户）
    int deleteUser(String userId);
    //根据用户名模糊搜索用户
    List<User> getLikeUserByName(String userName, int recordCount);
}
