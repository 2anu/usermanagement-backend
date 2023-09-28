package com.test.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import com.test.usermanagement.exception.ResourceNotFoundException;
import com.test.usermanagement.model.User;
import com.test.usermanagement.model.UserSearch;
import com.test.usermanagement.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserServiceImpl userService;

	@MockBean
	private UserRepository userRepository;

	private UserSearch userSearch;
	private Pageable pageable;

	@BeforeEach
	void setUp() {
		userSearch = new UserSearch();
		pageable = mock(Pageable.class);
	}

	@Test
	void testCreateUser() {
		User userToCreate = new User();
		userToCreate.setFirstName("Test");
		when(userRepository.save(userToCreate)).thenReturn(userToCreate);

		User createdUser = userService.createUser(userToCreate);

		assertNotNull(createdUser);
		verify(userRepository, times(1)).save(userToCreate);
	}

	@Test
	void testUpdateUser() {

		User originalUser = new User();
		originalUser.setId(1);
		User modifiedUser = new User();
		modifiedUser.setId(1);
		modifiedUser.setFirstName("UpdatedFirstName");

		when(userRepository.findById(1)).thenReturn(Optional.of(originalUser));
		when(userRepository.save(modifiedUser)).thenReturn(modifiedUser);

		User updatedUser = userService.updateUser(modifiedUser);

		assertNotNull(updatedUser);
		assertEquals("UpdatedFirstName", updatedUser.getFirstName());
		verify(userRepository, times(1)).findById(1);
		verify(userRepository, times(1)).save(modifiedUser);
	}

	@Test
	void testUpdateUserNotFound() {
		User modifiedUser = new User();
		modifiedUser.setId(1);
		when(userRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(modifiedUser));
	}

	@Test
	void testDeleteUser() {
		int userIdToDelete = 1;
		when(userRepository.findById(userIdToDelete)).thenReturn(Optional.of(new User()));

		userService.deleteUser(userIdToDelete);

		verify(userRepository, times(1)).delete(any()); // Verify that the delete method was called once
	}

	@Test
	void testDeleteUserNotFound() {
		int userIdToDelete = 1;
		when(userRepository.findById(userIdToDelete)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userIdToDelete));
	}

	@Test
	void testSearch() {
		userSearch.setFirstName("John");
		userSearch.setEmail("john@example.com");
		List<User> userList = new ArrayList<>();
		when(userRepository.findByFirstNameAndEmail("John", "john@example.com", pageable)).thenReturn(userList);

		List<User> result = userService.search(userSearch, pageable);

		assertNotNull(result);
		assertEquals(userList, result);
		verify(userRepository, times(1)).findByFirstNameAndEmail("John", "john@example.com", pageable);
	}

	@Test
	void testSearchWithFirstNameOnly() {
		userSearch.setFirstName("John");
		List<User> userList = new ArrayList<>();
		when(userRepository.findByFirstName("John", pageable)).thenReturn(userList);

		List<User> result = userService.search(userSearch, pageable);

		assertNotNull(result);
		assertEquals(userList, result);
		verify(userRepository, times(1)).findByFirstName("John", pageable);
	}

	@Test
	void testSearchWithEmailOnly() {
		userSearch.setEmail("john@example.com");
		List<User> userList = new ArrayList<>();
		when(userRepository.findByEmail("john@example.com", pageable)).thenReturn(userList);

		List<User> result = userService.search(userSearch, pageable);

		assertNotNull(result);
		assertEquals(userList, result);
		verify(userRepository, times(1)).findByEmail("john@example.com", pageable);
	}

	@Test
	void testSearchWithEmptyCriteria() {
		List<User> result = userService.search(userSearch, pageable);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verifyNoInteractions(userRepository);
	}

}
