package oss.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oss.dao.TestDao;
import oss.entity.Courseware;
import oss.entity.Page;
import oss.entity.Record_Join;
import oss.entity.Test_Table;
import oss.entity.User_Table;

@Service
public class TestService {
	@Autowired TestDao testDao;
	
	public List<Courseware> queryCourseWare(){
		return testDao.queryCourseWare();
	}

	public List<Record_Join> queryRecord(){
		return testDao.queryRecord();
	}
	
	public List<User_Table> queryUserById(@Param("Id") List<Integer> Id){
		return testDao.queryUserById(Id);
	}
	
	public int insretTest(List<Test_Table> test_Tables) {
		return testDao.insretTest(test_Tables);
	}
	
	public List<Test_Table> searchInvListTest(Page page){
		return testDao.searchInvListTest(page);
	}
	public List<Test_Table> getInvBycondtionTest(Page page){
		return testDao.getInvBycondtionTest(page);
	}
	public Integer searchTotalCountTest(Page page){
		return testDao.searchTotalCountTest(page);
	}
	
	public int findBatch() {
		return testDao.findBatch();
	}
}
