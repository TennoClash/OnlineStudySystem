package oss.dao;

import java.util.List;

import oss.entity.Course;
import oss.entity.Page;
import oss.entity.Study_Batch;
import oss.entity.User_Table;


public interface ManageDao {

	public List<User_Table> searchInvListT(Page page);

	public List<User_Table> getInvBycondtionT(Page page);

	public Integer searchTotalCountT(Page page);
	
	public int updateUser(User_Table user_Table);
	
	public int vPass(String id);
	
	public List<Course> searchInvListC(Page page);

	public List<Course> getInvBycondtionC(Page page);

	public Integer searchTotalCountC(Page page);
	
	public int addClazz(Course course);
	
	public int addBatch(Study_Batch study_Batch);
	
	public List<Study_Batch> searchInvListB(Page page);

	public List<Study_Batch> getInvBycondtionB(Page page);

	public Integer searchTotalCountB(Page page);
	
	public int baOpen(int id); 
	
	public int baClose(int id);
	
	public List<Course> getCourse();
	
	public List<Course> getCourseByBatch(int id);
}
