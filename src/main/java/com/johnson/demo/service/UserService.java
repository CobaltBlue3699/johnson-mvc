package com.johnson.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.johnson.core.mvc.annotation.Autowired;
import com.johnson.core.mvc.annotation.Service;
import com.johnson.demo.modal.IUserDao;
import com.johnson.demo.modal.User;

@Service
public class UserService implements IUserService {

	@Autowired
	IUserDao userDao;

	@Override
	public User getUserById(Long id) {
		return new User(id, "Johnson");
//		return userDao.findById(id);
	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		users.add(new User(1l, "johnson1"));
		users.add(new User(2l, "johnson2"));
		users.add(new User(3l, "johnson3"));
		users.add(new User(4l, "johnson4"));
		return users;
//		return userDao.findAll();
	}
}
