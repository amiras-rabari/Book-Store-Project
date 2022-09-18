package com.fdmgroup.bookstore.service;

import com.fdmgroup.bookstore.data.UserRepository;
import com.fdmgroup.bookstore.model.User;

import Exception.UserNotFoundException;

public class AuthenticationService {

	UserRepository userRepository;

	public AuthenticationService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User authenticate(String username, String password) throws UserNotFoundException {

		boolean result = userRepository.validate(username, password);
		if (result == true) {
			return userRepository.findByUsername(username);
		}
		if (result == false) {
			throw new UserNotFoundException("user does not exist");
		}
		return null;
	}

	public User findById(int Id) throws UserNotFoundException {

		User result = userRepository.findById(Id);
		if (result != null) {
			return result;
		}
		throw new UserNotFoundException("user does not exist");
	}
}
