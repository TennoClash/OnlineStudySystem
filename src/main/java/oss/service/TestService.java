package oss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import oss.dao.TestDao;
import oss.entity.Courseware;
import oss.entity.Record_Join;
import oss.entity.Study_Record;

@Service
public class TestService {
	@Autowired TestDao testDao;
	
	public List<Courseware> queryCourseWare(){
		return testDao.queryCourseWare();
	}

	public List<Record_Join> queryRecord(){
		return testDao.queryRecord();
	}
}
