package oss.dao;

import java.util.List;

import oss.entity.Courseware;
import oss.entity.Record_Join;

public interface TestDao {
public List<Courseware> queryCourseWare();

public List<Record_Join> queryRecord();
}
