package com.makiru.dao.user;

import com.makiru.dao.BaseDao;
import com.makiru.dao.book.BookDao;
import com.makiru.dao.book.BookDaoImpl;
import com.makiru.pojo.Reserve;
import com.makiru.pojo.Role;
import com.makiru.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    @Override
    public User getUserByName(String userName) {
        User user = null;
        String sql = "SELECT u.*, r.roleName FROM OBRS_user AS u, OBRS_role as r WHERE u.userRole = r.roleId AND u.userName = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Object[] params = {userName};
        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = BaseDao.execute(statement, resultSet, params);
            if(resultSet.next()){
                user = new User();
                user.setUserId(resultSet.getString("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setUserEmail(resultSet.getString("userEmail"));
                user.setGender(resultSet.getInt("gender"));
                user.setUserAddress(resultSet.getString("userAddress"));
                user.setUserBirthday(resultSet.getDate("userBirthday"));
                user.setUserPhone(resultSet.getString("userPhone"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setUserRoleName(resultSet.getString("roleName"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return user;
    }

    @Override
    public User getUserById(String id) {
        User user = null;
        String sql = "SELECT u.*, r.roleName FROM OBRS_user AS u, OBRS_role as r WHERE u.userRole = r.roleId AND u.userId = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Object[] params = {id};
        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = BaseDao.execute(statement, resultSet, params);
            if(resultSet.next()){
                user = new User();
                user.setUserId(resultSet.getString("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserEmail(resultSet.getString("userEmail"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setUserAddress(resultSet.getString("userAddress"));
                user.setUserBirthday(resultSet.getDate("userBirthday"));
                user.setUserPhone(resultSet.getString("userPhone"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setUserRoleName(resultSet.getString("roleName"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return user;
    }

    @Override
    public int updateUser(User user) {
        String sql = "update OBRS_user set userName = ?, " +
                "gender = ?, userPhone = ?, " +
                "userAddress = ?, userBirthday = ? where userId = ?";
        Object[] params = {user.getUserName(),
                user.getGender(), user.getUserPhone(),
                user.getUserAddress(), user.getUserBirthday(),
                user.getUserId()};
        Connection connection = null;
        PreparedStatement statement = null;
        int updateRow = 0;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            updateRow = BaseDao.execute(statement, params);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return updateRow;
    }

    @Override
    public int updatePwd(String userId, String newPwd) {
        String sql = "update OBRS_user set userPassword = ? where userId = ?";
        Object[] params = {newPwd, userId};

        Connection connection = null;
        PreparedStatement statement = null;
        int updateRow = 0;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql);
            updateRow = BaseDao.execute(statement, params);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return updateRow;
    }

    @Override
    public int insertUser(User user) {
        String sql = "insert into OBRS_user (userId, userName, userPassword, gender, userPhone, " +
                "userAddress, userBirthday, userEmail, userRole) values(?,?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUserId(), user.getUserName(), user.getUserPassword(), user.getGender(),
                user.getUserPhone(), user.getUserAddress(), user.getUserBirthday(), user.getUserEmail(), user.getUserRole()};

        Connection connection = null;
        PreparedStatement statement = null;
        int updateRow = 0;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            updateRow = BaseDao.execute(statement, params);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return updateRow;
    }

    @Override
    public List<Role> getRoleList() {
        List<Role> roleList = new ArrayList<>();
        String sql = "select * from OBRS_role";
        Object[] params = {};
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = BaseDao.execute(statement, resultSet, params);
            while (resultSet.next()){
                Role role = new Role();
                role.setRoleId(resultSet.getString("roleId"));
                role.setRoleName(resultSet.getString("roleName"));
                roleList.add(role);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return roleList;
    }

    @Override
    public List<User> getUserList(String userName, int userRole, int gender, int pageSize, int pageIndex) {
        List<User> userList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String baseSql = "select u.*, r.roleName from OBRS_user as u inner join OBRS_role as r on u.userRole = r.roleId";
        builder.append(baseSql);
        List<Object> params = new ArrayList<>();
        boolean tag = false;
        if(!StringUtils.isNullOrEmpty(userName)){
            builder.append(" where u.userName like ?");
            params.add("%" + userName + "%");
            tag = true;
        }
        if(userRole != -1){
            if(tag){
                builder.append(" and u.userRole = ?");
                params.add(userRole);
            }else{
                builder.append(" where u.userRole = ?");
                params.add(userRole);
                tag = true;
            }
        }
        if(gender != -1){
            if(tag){
                builder.append(" and u.gender = ?");
                params.add(gender);
            }else{
                builder.append(" where u.gender = ?");
                params.add(gender);
                tag = true;
            }
        }
        builder.append(" limit ?,?");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(builder.toString());
//            System.out.println("getUserList=>" + builder.toString());
            resultSet = BaseDao.execute(statement, resultSet, params.toArray());

            while (resultSet.next()){
                User user = new User();
                user.setUserId(resultSet.getString("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPhone(resultSet.getString("userPhone"));
                user.setGender(resultSet.getInt("gender"));
                user.setUserEmail(resultSet.getString("userEmail"));
                user.setUserRoleName(resultSet.getString("roleName"));
                userList.add(user);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }

        return userList;
    }

    @Override
    public int getUserCount(String userName, int userRole, int gender) {
        int count = 0;
        StringBuilder builder = new StringBuilder();
        String baseSql = "select count(1) as count from OBRS_user as u";
        builder.append(baseSql);
        List<Object> params = new ArrayList<>();
        boolean tag = false;
        if(!StringUtils.isNullOrEmpty(userName)){
            builder.append(" where u.userName like ?");
            params.add("%" + userName + "%");
            tag = true;
        }
        if(userRole != -1){
            if(tag){
                builder.append(" and u.userRole = ?");
                params.add(userRole);
            }else{
                builder.append(" where u.userRole = ?");
                params.add(userRole);
                tag = true;
            }
        }
        if(gender != -1){
            if(tag){
                builder.append(" and u.gender = ?");
                params.add(gender);
            }else{
                builder.append(" where u.gender = ?");
                params.add(gender);
                tag = true;
            }
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(builder.toString());
            resultSet = BaseDao.execute(statement, resultSet, params.toArray());
            if(resultSet.next()){
                count = resultSet.getInt("count");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }
        return count;
    }

    @Override
    public int deleteUser(String userId) {
        //先删除该用户的预约记录，并更新书籍状态
        BookDao bookDao = new BookDaoImpl();
        int count = bookDao.getReserveCount(userId, null, null, null, null);
        List<Reserve> reserveList = bookDao.getReserveList(userId, 1, count, null, null, null, null);
        Connection connection = null;
        PreparedStatement statement = null;
        int updateRow = 0;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            if(count > 0){
                for (Reserve reserve : reserveList) {
                    String bookId = reserve.getBookId();
                    String sql1 = "delete from OBRS_reserve where reserveId=? and bookId=? and userId=?";
                    Object[] params1 = {reserve.getReserveId(), bookId, userId};
                    String sql2 = "update OBRS_book set bookStatus=1 where bookId=?";
                    Object[] params2 = {bookId};

                    statement = connection.prepareStatement(sql1);
                    updateRow = BaseDao.execute(statement, params1);
                    statement = connection.prepareStatement(sql2);
                    updateRow = BaseDao.execute(statement, params2);
                }
            }
            //删除用户
            String sql = "delete from OBRS_user where userId = ?";
            Object[] params = {userId};
            statement = connection.prepareStatement(sql);
            updateRow = BaseDao.execute(statement, params);

            connection.commit();
            connection.setAutoCommit(true);

        } catch (ClassNotFoundException | SQLException e) {
            if(connection == null){
                return -1;
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, null);
        }
        return updateRow;
    }

    @Override
    public List<User> getLikeUserByName(String userName, int recordCount) {
        List<User> userList = new ArrayList<>();
        String sql = "select * from OBRS_user where userName like ? and userRole != 1 limit 0,?";
        Object[] params = {"%" + userName + "%", recordCount};
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = BaseDao.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = BaseDao.execute(statement, resultSet, params);

            while (resultSet.next()){
                User user = new User();
                user.setUserName(resultSet.getString("userName"));
                userList.add(user);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResources(connection, statement, resultSet);
        }

        return userList;
    }
}
