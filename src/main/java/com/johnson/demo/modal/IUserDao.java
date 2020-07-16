package com.johnson.demo.modal;

import java.util.List;

public interface IUserDao {
	public List<User> findAll();
	public User findById(Long id);
}
