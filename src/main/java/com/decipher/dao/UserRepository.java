package com.decipher.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.decipher.entities.UserEntity;
import com.decipher.response.UserResponse;

@Repository
public class UserRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NotificationRepository notificationRepository;

	public void addUser(UserEntity userEntity) {
		try {
			jdbcTemplate.update("insert into user (name,email,phone) value(?,?,?)", userEntity.getName(),
					userEntity.getEmail(), userEntity.getPhoneNo());
			System.out.println("User Is created");
		} catch (Exception e) {
		}
	}

	public boolean isEmailExist(String email) {
		try {
			String sql = "SELECT COUNT(*) FROM user where email=?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class,new Object[] { email });
			if (count > 0)
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public List<UserResponse> findAllUsers() {
		try {
			return jdbcTemplate.query("select * from user", new RowMapper<UserResponse>() {
				@Override
				public UserResponse mapRow(ResultSet rs, int rownumber) throws SQLException {
					UserResponse e = new UserResponse();
					e.setId(rs.getInt(1));
					e.setName(rs.getString(2));
					e.setEmail(rs.getString(3));
					e.setPhoneNo(rs.getString(4));
					System.out.println(e + "hi");
					return e;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}

	public int upDateUser(UserEntity userEntity) {
		try {
			int numberOfRowsAffected = jdbcTemplate.update(" update user set name=? ,phone=? where email =?",
					userEntity.getName(), userEntity.getPhoneNo(), userEntity.getEmail());
			System.out.println("User Is created");
			return numberOfRowsAffected;

		} catch (Exception e) {
			return 0;
		}
	}

	public int deleteRecord(String email) {
		try {
			int deleteRowCount = jdbcTemplate.update(" Delete from user where email=?", email);
			System.out.println("Deleted");
			return deleteRowCount;
		} catch (Exception e) {
			return 0;
		}
	}

	public String getEmailByuserId(int id) {
		try {
			String sql = "SELECT * from USER WHERE id=?";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[] { id });
			String emailId = (String) list.get(0).get("email");
			System.out.println(list);
			return emailId;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Map<String, Object>> findUserRecordById(int userId) {
		try {
			String sql = "select * from user where id=?";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[] { userId });		
			List<Map<String, Object>> listNotification = notificationRepository.findAllNotificationByUserId(userId);
			for (Map<String, Object> hm : list) {
				hm.put("notification", listNotification);
			}
			return list;

		} catch (Exception e) {
			return null;
		}
	}


}
