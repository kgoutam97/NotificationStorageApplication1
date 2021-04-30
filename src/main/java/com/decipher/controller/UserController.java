package com.decipher.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decipher.dao.UserRepository;
import com.decipher.request.UserRequest;
import com.decipher.response.UserResponse;
import com.decipher.services.UserServices;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserServices userService;	
	
	@Autowired
	UserRepository userRepository;
	 
	@PostMapping("/saveUser")
	ResponseEntity<String> SaveUser(@RequestBody UserRequest user) {
		System.out.println(userService.checkEmailExistOrNot(user.getEmail()));
		if(userService.checkEmailExistOrNot(user.getEmail())) {
			return new ResponseEntity<>("This User Account Is Already exist in DataBase Try With Another Email",HttpStatus.OK);		   			
		}
		userService.saveUserData(user);
		 return new ResponseEntity<>("user IS successfull Stored!", HttpStatus.OK);			
	}
	
	@GetMapping("/getAllUsers")
	List<UserResponse> getUsersData(){
		List<UserResponse> list=new ArrayList<UserResponse>();
		list.addAll(userService.getAlllUsers());
		System.out.println(list);		
		return list;
	}
	
	@PutMapping("/updateUser")
	ResponseEntity<String> uppdateUser(@RequestBody UserRequest user) {
		System.out.println(userService.checkEmailExistOrNot(user.getEmail()));
		if(userService.checkEmailExistOrNot(user.getEmail())) {
			int updateRecordCount=userService.updateUserRecord(user);
			return new ResponseEntity<>(updateRecordCount+ "Record is updated",HttpStatus.OK);		   			
		}
		 return new ResponseEntity<>("this User Record is not Exist in The DataBase", HttpStatus.OK);			
	}
	
	
	@DeleteMapping("/deleteUser/{email}")
	ResponseEntity<String> deleteUser(@PathVariable String email) {
		System.out.println(userService.checkEmailExistOrNot(email));
		if(userService.checkEmailExistOrNot(email)) {
			int deleteRecordCount=userService.deleteByEmail(email);
			//userService.saveUserData(user);
			return new ResponseEntity<>(deleteRecordCount+ " Record is deleted",HttpStatus.OK);		   			
		}
		 return new ResponseEntity<>("this User Record is not Exist in The DataBase", HttpStatus.OK);			
	}
	
	@GetMapping("/getUserWithNotification/{userId}")
	List<Map<String, Object>> userRecordById(@PathVariable int userId){		
		return userService.userwithNotification(userId);
	}	
	

	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello World";
	}	

}
