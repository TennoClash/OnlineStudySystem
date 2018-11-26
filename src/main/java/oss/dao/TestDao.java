package oss.dao;

import java.util.List;

import javax.print.attribute.standard.Fidelity;

import org.apache.ibatis.annotations.Param;

import junit.framework.Test;
import oss.entity.Courseware;
import oss.entity.Page;
import oss.entity.Record_Join;
import oss.entity.Test_Table;
import oss.entity.User_Table;

public interface TestDao {
	
public List<Courseware> queryCourseWare();

public List<Record_Join> queryRecord();

public List<User_Table> queryUserById(@Param("Id") List<Integer> Id);

public int insretTest(List<Test_Table> test_Tables);


public List<Test_Table> searchInvListTest(Page page);

public List<Test_Table> getInvBycondtionTest(Page page);

public Integer searchTotalCountTest(Page page);

public int findBatch();

}
