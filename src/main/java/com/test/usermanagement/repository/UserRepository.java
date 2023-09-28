package com.test.usermanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.usermanagement.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByEmail(String email, Pageable pageable);

	List<User> findByFirstName(String firstName, Pageable pageable);

	List<User> findByFirstNameAndEmail(String firstName, String email, Pageable pageable);

}
