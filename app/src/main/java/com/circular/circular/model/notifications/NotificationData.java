package com.circular.circular.model.notifications;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotificationData {

	@SerializedName("notifications")
	private List<NotificationsItem> notifications;

	public List<NotificationsItem> getNotifications(){
		return notifications;
	}
}