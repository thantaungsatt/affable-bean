package com.example.demo.service;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dao.OtpDao;
import com.example.demo.dao.RoleDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.Otp;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;

@Service
public class AuthService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private OtpDao otpDao;
	
	public boolean validateOtp(String username, String code) {
		Optional<Otp>otpOptional = otpDao.findByCodeAndUsername(code, username);
		if(otpOptional.isPresent()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String register(String userName, String password, String email) {
		var user = new User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setUsername(userName);
		
		Optional<Role> userRole=roleDao.findByRoleName("ROLE_USER");
		userRole.ifPresentOrElse(r->{
			user.getRoles().add(r);
			userDao.save(user);
		}, ()->{
			Role roleUser = new Role();
			roleUser.setRoleName("ROLE_USER");
			roleDao.save(roleUser);
			user.getRoles().add(roleUser);
			userDao.save(user);
		});
		return "Successfully Register!";
	}
	
	public String login(String username, String password) {
		var auth = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication=authenticationManager.authenticate(auth);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		renewOtp(authentication.getName());
		return "Successfull login!";
	}
	
	private void renewOtp(String username) {
		String code = generateOpt();
		otpDao.findByUsername(username).ifPresentOrElse(otp->{
			otp.setCode(code);
			otpDao.saveAndFlush(otp);
		}, ()->{
			Otp otp = new Otp();
			otp.setUsername(username);
			otp.setCode(code);
			otpDao.save(otp);
		});
	}
	
	private String generateOpt() {
		SecureRandom random = new SecureRandom();
		int code = random.nextInt(1000)+1000;
		return new StringBuilder().append(code).toString();
	}
}
