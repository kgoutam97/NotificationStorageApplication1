package com.decipher.request;

import lombok.Data;

public @Data class NotificationRequest {

	private String notificationType;
	private int user;
	private String notifyTime;
	private String dateCreated;
	private String lastUpdated;
	private boolean enabled;
	private boolean isSent;
	private boolean isRepeat;

}
