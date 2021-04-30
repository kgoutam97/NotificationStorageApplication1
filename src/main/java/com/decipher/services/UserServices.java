package com.decipher.services;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decipher.dao.UserRepository;
import com.decipher.entities.UserEntity;
import com.decipher.request.UserRequest;
import com.decipher.response.UserResponse;

@Service
public class UserServices {

	UserEntity userEntity = new UserEntity();

	@Autowired
	UserRepository userRepository;

	public void saveUserData(UserRequest userRequest) {
		ModelMapper modelMapper = new ModelMapper();
		userEntity=modelMapper.map(userRequest, UserEntity.class);
		userRepository.addUser(userEntity);
		System.out.println("UserEntity:-" + userEntity);
	}

	public boolean checkEmailExistOrNot(String email) {
		return userRepository.isEmailExist(email);

	}

	public List<UserResponse> getAlllUsers() {
		System.out.println(userRepository.findAllUsers() + "output");
		return userRepository.findAllUsers();
	}

	public int updateUserRecord(UserRequest userRequest) {
		ModelMapper modelMapper = new ModelMapper();
		userEntity=modelMapper.map(userRequest, UserEntity.class);
		return userRepository.upDateUser(userEntity);
	}

	public int deleteByEmail(String email) {
		return userRepository.deleteRecord(email);
	}

	public String getEmailId(int id) {
		return userRepository.getEmailByuserId(id);
	}

	public List<Map<String, Object>> userwithNotification(int userId) {
		return userRepository.findUserRecordById(userId);
	}

}
