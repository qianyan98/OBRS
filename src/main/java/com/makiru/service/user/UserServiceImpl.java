package com.makiru.service.user;

import com.makiru.dao.user.UserDao;
import com.makiru.dao.user.UserDaoImpl;
import com.makiru.pojo.Role;
import com.makiru.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.util.List;

public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public User getUserByName(String userName) {
        User user = null;
        if(!StringUtils.isNullOrEmpty(userName)){
            user = userDao.getUserByName(userName);
        }
        return user;
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int updatePwd(String userId, String newPwd) {
        return userDao.updatePwd(userId, newPwd);
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public List<Role> getRoleList() {
        return userDao.getRoleList();
    }

    @Override
    public List<User> getUserList(String userName, int userRole, int gender, int pageSize, int pageIndex) {
        return userDao.getUserList(userName, userRole, gender, pageSize, pageIndex);
    }

    @Override
    public int getUserCount(String userName, int userRole, int gender) {
        return userDao.getUserCount(userName, userRole, gender);
    }

    @Override
    public int deleteUser(String userId) {
        return userDao.deleteUser(userId);
    }

    @Override
    public List<User> getLikeUserByName(String userName, int recordCount) {
        return userDao.getLikeUserByName(userName, recordCount);
    }
}
