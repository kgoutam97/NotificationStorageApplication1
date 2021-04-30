package com.decipher.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.decipher.request.NotificationRequest;
import com.decipher.response.NotificationResponse;
import com.decipher.services.NotificationServices;
import com.decipher.services.UserServices;

@RestController
@RequestMapping("/user")
public class NotificationController {

	@Autowired
	NotificationServices notificationServices;

	@Autowired
	UserServices userService;

	@GetMapping("/getAllNotification")
	List<NotificationResponse> getAllNotification() {
		List<NotificationResponse> list = new ArrayList<NotificationResponse>();
		list.addAll(notificationServices.getAllNotification());
		System.out.println(list);
		return list;
	}

	@PostMapping("/saveNotification")
	ResponseEntity<String> saveNotification(@RequestBody NotificationRequest notificationRequest) {
		if (userService.checkEmailExistOrNot(userService.getEmailId(notificationRequest.getUser()))) {
			notificationServices.saveNotification(notificationRequest);
			return new ResponseEntity<>("The Notification has inserted in Db", HttpStatus.OK);
		}
		return new ResponseEntity<>("user email id is not Exist in DataBase PLease Enter Currect Email!",
				HttpStatus.OK);

	}

	@DeleteMapping("/deleteNotification/{notificationId}")
	ResponseEntity<String> deleteNotification(@PathVariable int notificationId) {
		if (notificationServices.isNOtification(notificationId)) {
			int deleteRecordCount = notificationServices.deleteByNotificationId(notificationId);
			return new ResponseEntity<>(deleteRecordCount + "  Notification Record is deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("this Notification  is not Exist in The DataBase", HttpStatus.OK);

	}

	@PutMapping("/updateNotification/{notificationId}")
	ResponseEntity<String> notificationUpdate(@RequestBody NotificationRequest notificationRequest,
			@PathVariable int notificationId) {
		if (notificationServices.isNOtification(notificationId)
				&& userService.checkEmailExistOrNot(userService.getEmailId(notificationRequest.getUser()))) {
			int updateRecordCount = notificationServices.updateNotificationRecord(notificationRequest, notificationId);
			return new ResponseEntity<>(updateRecordCount + " Record is updated", HttpStatus.OK);
		}
		return new ResponseEntity<>("this Notification is not Exist or This UserEmail is not Exist try again",
				HttpStatus.OK);
	}

	@PutMapping("/updateNotificationStatus/params")
	ResponseEntity<String> updateNotificationStatus(@RequestParam("notification_id") int notificationId,
			@RequestParam("enabled") boolean enabled) {
		if (notificationServices.isNOtification(notificationId)) {
			notificationServices.upDateNotificationStatus(notificationId, enabled);
			return new ResponseEntity<>("Notification enabled set to " + enabled, HttpStatus.OK);
		}
		return new ResponseEntity<>("this Notification is not Exist", HttpStatus.OK);
	}

	@GetMapping("/getFilteredNotification/params")
	List<Map<String, Object>> getAllFilteredNotification(@RequestParam("notifyTime") String notifyTime,
			@RequestParam("notificationType") String notificationType, @RequestParam("userId") Integer userId) {
		if (notifyTime.length() > 0 || notificationType.length() > 0 || userId > 0)
			return notificationServices.notiFicationfilter(notifyTime, notificationType, userId);
		else {
			List<Map<String, Object>> list = new ArrayList<>();
			Map<String, Object> hm = new HashMap<String, Object>();
			Object[] obj = { "please Enter At least one Filter" };
			hm.put("response", obj);
			list.add(hm);
			return list;
		}
	}

}
