package oss.dao;

import java.util.List;

import oss.entity.Batch_CourseKey;
import oss.entity.Course;
import oss.entity.Courseware;
import oss.entity.Page;
import oss.entity.User_BatchKey;

public interface CourseWareDao {

	public int addCourseWare(Courseware courseware);
	
	
	public List<Courseware> searchInvListC(Page page);

	public List<Courseware> getInvBycondtionC(Page page);

	public Integer searchTotalCountC(Page page);
	
	public int updateCourseWare(Courseware courseware);
	
	public User_BatchKey isUserBatch(User_BatchKey user_BatchKey);
	
	public int addUserBatch(User_BatchKey user_BatchKey);
	
	public List<Course> getBatchCourse(int id);
	
	public List<Courseware> getCourseWareByCourseId(int id);
}
