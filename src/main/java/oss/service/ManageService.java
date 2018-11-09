package oss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oss.dao.ManageDao;
import oss.entity.Course;
import oss.entity.Page;
import oss.entity.Study_Batch;
import oss.entity.User_Table;



@Service
public class ManageService {
	@Autowired private ManageDao manageDao;
	
	public List<User_Table> searchInvListT(Page page){
		return manageDao.searchInvListT(page);
	}
	public List<User_Table> getInvBycondtionT(Page page){
		return manageDao.getInvBycondtionT(page);
	}
	public Integer searchTotalCountT(Page page){
		return manageDao.searchTotalCountT(page);
	}
	public int updateUser(User_Table user_Table) {
		return manageDao.updateUser(user_Table);
	}
	public int vPass(String id) {
		return manageDao.vPass(id);
	}
	
	public List<Course> searchInvListC(Page page){
		return manageDao.searchInvListC(page);
	}
	public List<Course> getInvBycondtionC(Page page){
		return manageDao.getInvBycondtionC(page);
	}
	public Integer searchTotalCountC(Page page){
		return manageDao.searchTotalCountC(page);
	}
	
	
	public int addClazz(Course course) {
		return manageDao.addClazz(course);
	}
	public int addBatch(Study_Batch study_Batch) {
		return manageDao.addBatch(study_Batch);
	}
	
	public List<Study_Batch> searchInvListB(Page page){
		return manageDao.searchInvListB(page);
	}
	public List<Study_Batch> getInvBycondtionB(Page page){
		return manageDao.getInvBycondtionB(page);
	}
	public Integer searchTotalCountB(Page page){
		return manageDao.searchTotalCountB(page);
	}
	
	public int baOpen(int id) {
		return manageDao.baOpen(id);
	}
	public int baClose(int id) {
		return manageDao.baClose(id);
	}
	public List<Course> getCourse(){
		return manageDao.getCourse();
	}
	public List<Course> getCourseByBatch(int id){
		return manageDao.getCourseByBatch(id);
	}
	
}
