package com.test.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.usermanagement.model.User;
import com.test.usermanagement.model.UserSearch;
import com.test.usermanagement.model.UserView;
import com.test.usermanagement.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@CrossOrigin
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return ResponseEntity.ok().body(this.userService.createUser(user));
	}

	@CrossOrigin
	@PutMapping("/users")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		return ResponseEntity.ok().body(this.userService.updateUser(user));
	}

	@CrossOrigin
	@DeleteMapping("/users/{id}")
	public HttpStatus deleteUser(@PathVariable Integer id) {
		this.userService.deleteUser(id);
		return HttpStatus.OK;
	}

	@CrossOrigin
	@GetMapping("users")
	public ResponseEntity<UserView> getAllUsers(@PageableDefault(size = 5) Pageable pageable) {
		return ResponseEntity.ok().body(new UserView(this.userService.getAll(pageable), this.userService.getCount()));
	}

	@CrossOrigin
	@PostMapping("/users/search")
	public ResponseEntity<UserView> searchUsers(@RequestBody UserSearch user,
			@PageableDefault(size = 5) Pageable pageable) {
		return ResponseEntity.ok()
				.body(new UserView(this.userService.search(user, pageable), this.userService.getCount()));
	}

}
