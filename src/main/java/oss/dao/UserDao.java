package oss.dao;

import oss.entity.User_Table;

public interface UserDao {
	User_Table getUser(User_Table user);

	User_Table getUserByName(String i);
	
	int registration(User_Table user_Table);
	
	Integer getRole_Id(int id);
	
	int updataUserRole(int i);
}
