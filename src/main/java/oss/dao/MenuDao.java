package oss.dao;

import java.util.List;

import oss.entity.Menu;

public interface MenuDao {
	List<Menu> menuX(int i);
	List<Menu> getTreeMenu(int i);
	int updateByPrimaryKeySelective(Menu record);
	int addMenu(Menu menu);
	int orderplus(Menu menu);
	int getSortById(int i);
	int orderreduce(Menu menu);
	int deleteByPrimaryKey(int i);
}
