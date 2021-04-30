package com.decipher.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.decipher.entities.NotificationEntity;
import com.decipher.response.NotificationResponse;

@Repository
public class NotificationRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<NotificationResponse> findAllNotification() {

		return jdbcTemplate.query("select * from notification", new RowMapper<NotificationResponse>() {
			@Override
			public NotificationResponse mapRow(ResultSet rs, int rownumber) throws SQLException {

				NotificationResponse notification = new NotificationResponse();
				notification.setNotificationId(rs.getInt(1));
				notification.setNotificationType(rs.getString(2));
				notification.setUser(rs.getInt("user"));
				notification.setNotifyTime(rs.getString(4));
				notification.setDateCreated(rs.getString(5));
				notification.setLastUpdated(rs.getString(6));
				notification.setEnabled(rs.getBoolean(7));
				notification.setSent(rs.getBoolean(8));
				notification.setRepeat(rs.getBoolean(9));
				return notification;
			}
		});
	}

	public void addNotification(NotificationEntity notificationEntity) {
		try {
			String query = "insert into notification(notificationType,user,notifyTime,dateCreated,lastUpdated,enabled,isSent,isRepeat) value(?,?,?,?,?,?,?,?)";
			int i = jdbcTemplate.update(query, notificationEntity.getNotificationType(), notificationEntity.getUser(),
					notificationEntity.getNotifyTime(), notificationEntity.getDateCreated(),
					notificationEntity.getLastUpdated(), notificationEntity.isEnabled(), notificationEntity.isSent(),
					notificationEntity.isRepeat());
			System.out.println("Notification is created Is created" + i);
		} catch (Exception e) {
		}
	}

	public int deleteNotification(int notificationId) {
		try {
			int deleteRowCount = jdbcTemplate.update("Delete from notification where notification_id=?",
					notificationId);
			System.out.println("Deleted");
			return deleteRowCount;
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean isNotificationExist(int notificationId) {
		try {
			String sql = "SELECT COUNT(*) FROM notification where notification_id=?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { notificationId });
			if (count > 0)
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public int upDateNotification(NotificationEntity notificationEntity, int notificationId) {
		try {
			int numberOfRowsAffected = jdbcTemplate.update(
					"update notification set notificationType=? ,user=?,notifyTime=?,dateCreated=?,lastUpdated=?, enabled=?,isSent=?,isRepeat=? where notification_id=?",
					notificationEntity.getNotificationType(), notificationEntity.getUser(),
					notificationEntity.getNotifyTime(), notificationEntity.getDateCreated(),
					notificationEntity.getLastUpdated(), notificationEntity.isEnabled(), notificationEntity.isSent(),
					notificationEntity.isRepeat(), notificationId);
			System.out.println("User Is created");
			return numberOfRowsAffected;
		} catch (Exception e) {
			return 0;
		}

	}

	public void setEnableDisableNotification(int notificationId, boolean notificationStatus) {
		try {
			jdbcTemplate.update("update notification set enabled=? where notification_id=?", notificationStatus,
					notificationId);
			System.out.println("Notification status  Is updated");
		} catch (Exception e) {
		}
	}

	public List<Map<String, Object>> notifiCationFilterWiseData(String notifyTime, String notificationType,
			int userId) {
		try {
			String sql = "select * from notification where CAST(notifyTime as DATE) = ? or notificationType =? or user= ?";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,
					new Object[] { notifyTime, notificationType, userId });
			return list;

		} catch (Exception e) {
			return null;
		}
	}

	public List<Map<String, Object>> findAllNotificationByUserId(int userId) {
		try {
			String sql = "select * from notification where user= ?";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[] { userId });
			return list;

		} catch (Exception e) {
			return null;
		}
	}

}
