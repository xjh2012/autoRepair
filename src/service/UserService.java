package service;

import model.UserView;

import java.util.List;

public interface UserService {

	public List<UserView> getUserList();
	public List<UserView> getUserByParam(UserView searchUser);
	
	public Integer createUser(UserView user);
	public Integer updateUser(UserView user);
	public Integer deleteUser(UserView user);
	
}
