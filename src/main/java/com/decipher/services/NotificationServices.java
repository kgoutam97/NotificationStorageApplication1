package com.decipher.services;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decipher.dao.NotificationRepository;
import com.decipher.entities.NotificationEntity;
import com.decipher.request.NotificationRequest;
import com.decipher.response.NotificationResponse;

@Service
public class NotificationServices {

	@Autowired
	NotificationRepository notificationRepository;

	
	public List<NotificationResponse> getAllNotification() {
		return notificationRepository.findAllNotification();
	}

	
	public void saveNotification(NotificationRequest notificationRequest) {
		ModelMapper modelMapper = new ModelMapper();
		NotificationEntity notificationEntity = modelMapper.map(notificationRequest, NotificationEntity.class);
		notificationRepository.addNotification(notificationEntity);
	}
	
     public int deleteByNotificationId(int notificationId) {		
 		return notificationRepository.deleteNotification(notificationId);		
	}
     
     public boolean isNOtification(int notificationId) {		
 		return notificationRepository.isNotificationExist(notificationId);
 		
 	}
         
     public int updateNotificationRecord(NotificationRequest notificationRequest,int notificationId) {
 		ModelMapper modelMapper = new ModelMapper();
 		NotificationEntity notificationEntity = modelMapper.map(notificationRequest, NotificationEntity.class);
  		return notificationRepository.upDateNotification(notificationEntity, notificationId);				
 	}
     
     public void upDateNotificationStatus(int notificationId,boolean notificationStatus) {
    	 notificationRepository.setEnableDisableNotification(notificationId, notificationStatus);
     }
     
		public List<Map<String, Object>> notiFicationfilter(String notifyTime,String notificationType,int userId){
    	 return notificationRepository.notifiCationFilterWiseData(notifyTime, notificationType, userId);
     }

}
