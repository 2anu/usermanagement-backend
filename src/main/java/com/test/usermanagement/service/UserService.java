package com.test.usermanagement.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.test.usermanagement.model.User;
import com.test.usermanagement.model.UserSearch;

public interface UserService {

	User createUser(User user);

	User updateUser(User user);

	void deleteUser(Integer id);

	List<User> getAll(Pageable pageable);

	List<User> search(UserSearch userSearch, Pageable pageable);
	
	Long getCount();

}
