package oss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oss.dao.CourseWareDao;
import oss.entity.Batch_CourseKey;
import oss.entity.Course;
import oss.entity.Courseware;
import oss.entity.Page;
import oss.entity.Record_Join;
import oss.entity.Study_Record;
import oss.entity.User_BatchKey;

@Service
public class CourseWareService {
@Autowired CourseWareDao courseWareDao;
	public int addCourseWare(Courseware courseware) {
		return courseWareDao.addCourseWare(courseware);
	}
	
	
	public List<Courseware> searchInvListC(Page page){
		return courseWareDao.searchInvListC(page);
	}

	public List<Courseware> getInvBycondtionC(Page page){
		return courseWareDao.getInvBycondtionC(page);
	}

	public Integer searchTotalCountC(Page page) {
		return courseWareDao.searchTotalCountC(page);
	}
	
	public int updateCourseWare(Courseware courseware) {
		return courseWareDao.updateCourseWare(courseware);
	}
	
	public User_BatchKey isUserBatch(User_BatchKey user_BatchKey) {
		return courseWareDao.isUserBatch(user_BatchKey);
	}
	
	public int addUserBatch(User_BatchKey user_BatchKey) {
		return courseWareDao.addUserBatch(user_BatchKey);
	}
	
	public List<Course> getBatchCourse(int id){
		return courseWareDao.getBatchCourse(id);
	}
	
	public List<Courseware> getCourseWareByCourseId(int id){
		return courseWareDao.getCourseWareByCourseId(id);
	}
	
	public int upCurrentTime(Study_Record study_Record) {
		return courseWareDao.upCurrentTime(study_Record);
	}
	
	public Study_Record isRecordExist(Study_Record study_Record) {
		return courseWareDao.isRecordExist(study_Record);
	}
	
	public int upCurrentTime2(Study_Record study_Record) {
		return courseWareDao.upCurrentTime2(study_Record);
	}
	
	public List<Record_Join> searchInvListR(Page page){
		return courseWareDao.searchInvListR(page);
	}

	public List<Record_Join> getInvBycondtionR(Page page){
		return courseWareDao.getInvBycondtionR(page);
	}

	public Integer searchTotalCountR(Page page) {
		return courseWareDao.searchTotalCountR(page);
	}
}
