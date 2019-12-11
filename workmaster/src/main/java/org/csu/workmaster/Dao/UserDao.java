package org.csu.workmaster.Dao;

import org.csu.workmaster.Entity.User;

public interface UserDao {

    public void saveUser(User user);
    public void updateUser(User user);
    public void deleteUserById(Long id);
    public User findUserByUserId(long id);

    public int updatePsw(long userId, String psw);
    public int updateAvatar(long userId, String Avator);
    public void updateGroup(long userId, long GroupId);
    public User findUserByusername(String username);
    public User findUserByStudentid(String studentid);
}
