package com.CRT.Project2.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CRT.Project2.entities.User;
import com.CRT.Project2.repositories.UserRepository;
import com.CRT.Project2.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;		
		this.passwordEncoder =passwordEncoder;
		this.jwtService = jwtService;
	}
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthRequest request){
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body("Email Already Exsits!");
		}
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		userRepository.save(user);
		return ResponseEntity.ok("User Registered Successfully!");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request){
		Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
		if(userOpt.isPresent() && passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
			String token = jwtService.generateToken(userOpt.get().getEmail(), userOpt.get().getId());
			return ResponseEntity.ok(Map.of("token", token));
		}
		return ResponseEntity.status(401).body("Invalid User");
	}
}
	
//	Gemini
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
//	    // 1. Fetch the user (wrapped in an Optional)
//	    var optionalUser = userRepository.findByEmail(request.getEmail());
//
//	    // Yes, using isEmpty() is perfect here
//	    if (optionalUser.isEmpty()) {
//	        return ResponseEntity.status(401).body("Invalid email or password");
//	    }
//
//	    User user = optionalUser.get();
//
//	    // 2. Verify the password matches the hash in the database
//	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//	        return ResponseEntity.status(401).body("Invalid email or password");
//	    }
//
//	    // 3. Generate the JWT token (assuming your JwtService has a generateToken method)
//	    // You might need to adjust the method name based on your JwtService implementation
//	    String token = jwtService.generateToken(user.getEmail(), user.getId()); 
//
//	    return ResponseEntity.ok(token); 
//	}
//}


class AuthRequest{
	private String email;
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}