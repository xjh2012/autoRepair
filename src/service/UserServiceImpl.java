package service;

import dao.UserDao;
import model.UserView;

import java.util.List;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public List<UserView> getUserList() {
		return userDao.getUserList();
	}

	public List<UserView> getUserByParam(UserView searchUser) {
		return userDao.getUserByParam(searchUser);
	}

	public Integer createUser(UserView user) {
		return userDao.createUser(user);
	}

	public Integer updateUser(UserView user) {
		return userDao.updateUser(user);	}

	public Integer deleteUser(UserView user) {
		return userDao.deleteUser(user);	

	}
	
	
}
