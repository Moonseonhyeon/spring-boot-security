package com.cos.securityex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

//@RestController
@Controller
public class IndexController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping({"", "/"})
	public @ResponseBody String index() {
		return "인덱스 페이지 입니다.";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) { // 세션찾기 어노테이션
		System.out.println(principal);
		System.out.println(principal.getUser().getRole());
		System.out.println(principal.getAuthorities()); //이 부분 완성해야 함.
		return "유저 페이지 입니다.";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "어드민 페이지 입니다.";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login"; //mustache사용하고 있으니까 prefix, surfix
	}
	
	@GetMapping("/join")
	public String join() {
		return "join"; 
	}
	
	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입 진행 : "+user);
		
		  String rawPassword = user.getPassword();
		  String encPassword = bCryptPasswordEncoder.encode(rawPassword); 
		  user.setPassword(encPassword);
		 user.setRole("ROLE_USER");
		userRepository.save(user);
		return "redirect:/"; 
	}
	
}
