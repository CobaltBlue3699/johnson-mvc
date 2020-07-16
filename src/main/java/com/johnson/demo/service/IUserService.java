package com.johnson.demo.service;

import java.util.List;

import com.johnson.demo.modal.User;

public interface IUserService {
	User getUserById(Long id);
	List<User> findAll();
}
