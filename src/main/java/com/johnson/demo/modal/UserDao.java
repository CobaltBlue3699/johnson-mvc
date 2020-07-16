package com.johnson.demo.modal;

import java.util.List;

import com.johnson.core.mvc.annotation.Bean;
import com.johnson.core.mvc.helper.DatabaseHelper;

@Bean
public class UserDao implements IUserDao {
	
	@Override
	public List<User> findAll() {
		return DatabaseHelper.queryEntityList(User.class, "SELECT * FROM USER;");
	}

	@Override
	public User findById(Long id) {
		return DatabaseHelper.queryEntity(User.class, "SELECT * FROM USER WHERE ID = ?", id);
	}

}
