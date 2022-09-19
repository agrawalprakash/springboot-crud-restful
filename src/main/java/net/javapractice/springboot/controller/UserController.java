package net.javapractice.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.javapractice.springboot.entity.User;
import net.javapractice.springboot.exception.ResourceNotFoundException;
import net.javapractice.springboot.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
@Autowired
private UserRepository userRepository;

@GetMapping
public List<User> getAllUsers() {
	
	return this.userRepository.findAll();
}

@GetMapping("/{id}")
public User getUserById (@PathVariable (value = "id") long userId) {
	return this.userRepository.findById(userId)
			.orElseThrow(() -> 
			new ResourceNotFoundException("User not found for this id"));
}
	

@PostMapping
public User createUser(@RequestBody User user) {
	
	return this.userRepository.save(user);
}

@PutMapping("/{id}")
public User updateUser(@RequestBody User user, @PathVariable ("id") long userId) {
	
	User existingUser = this.userRepository.findById(userId).orElseThrow(() -> 
	new ResourceNotFoundException("User not found for this id"));
	
	existingUser.setFirstName(user.getFirstName());
	existingUser.setLastName(user.getLastName());
	existingUser.setEmail(user.getEmail());
	this.userRepository.save(existingUser);
	return existingUser;
}


@DeleteMapping ("/id")
public ResponseEntity<User> deleteUser( @PathVariable ("id") long userId) {
	
	User existingUser = this.userRepository.findById(userId).orElseThrow(() -> 
	new ResourceNotFoundException("User not found for this id"));
	
	this.userRepository.delete(existingUser);
	
	return ResponseEntity.ok().build();
	
}

}
