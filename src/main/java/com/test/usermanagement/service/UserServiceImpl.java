package com.test.usermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.test.usermanagement.exception.ResourceNotFoundException;
import com.test.usermanagement.model.User;
import com.test.usermanagement.model.UserSearch;
import com.test.usermanagement.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		Optional<User> userDb = this.userRepository.findById(user.getId());

		if (userDb.isPresent()) {
			User userUpdate = userDb.get();
			userUpdate.setFirstName(user.getFirstName());
			userUpdate.setLastName(user.getLastName());
			userUpdate.setEmail(user.getEmail());
			userUpdate.setMobileNumber(user.getMobileNumber());
			userUpdate.setStatus(user.getStatus());
			userUpdate.setDob(user.getDob());
			userUpdate.setAddress(user.getAddress());
			userRepository.save(userUpdate);
			return userUpdate;
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + user.getId());
		}
	}

	@Override
	public void deleteUser(Integer id) {
		Optional<User> userDb = this.userRepository.findById(id);

		if (userDb.isPresent()) {
			this.userRepository.delete(userDb.get());
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + id);
		}

	}

	@Override
	public List<User> getAll(Pageable pageable) {
		return this.userRepository.findAll(pageable).toList();
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<User> search(UserSearch userSearch, Pageable pageable) {
		if (!StringUtils.isEmpty(userSearch.getFirstName()) && !StringUtils.isEmpty(userSearch.getEmail())) {
			return this.userRepository.findByFirstNameAndEmail(userSearch.getFirstName(), userSearch.getEmail(),
					pageable);
		} else if (StringUtils.isEmpty(userSearch.getFirstName()) && !StringUtils.isEmpty(userSearch.getEmail())) {
			return this.userRepository.findByEmail(userSearch.getEmail(), pageable);
		} else if (!StringUtils.isEmpty(userSearch.getFirstName()) && StringUtils.isEmpty(userSearch.getEmail())) {
			return this.userRepository.findByFirstName(userSearch.getFirstName(), pageable);
		}
		return new ArrayList<>();
	}

	@Override
	public Long getCount() {
		return userRepository.count();
	}

}
