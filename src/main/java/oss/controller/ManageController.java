package oss.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.soap.SOAPBinding.Style;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oss.entity.Batch_CourseKey;
import oss.entity.Course;
import oss.entity.Page;
import oss.entity.Study_Batch;
import oss.entity.User_Table;
import oss.service.ManageService;

@Controller
public class ManageController {
	@Autowired
	private ManageService manageService;

	@RequestMapping("/studycourse")
	public String studycourse() {
		return "studycourse";
	}

	@RequestMapping("/testq")
	public String testq() {
		return "testq";
	}

	@RequestMapping("/stest")
	public String stest() {
		return "stest";
	}

	@RequestMapping("/testmanage")
	public String testmanage() {
		return "testmanage";
	}

	@RequestMapping("/adminrecord")
	public String adminrecord() {
		return "adminrecord";
	}

	@RequestMapping("/srecord")
	public String srecord() {
		return "srecord";
	}

	@RequestMapping("/userma")
	public String userMa() {
		return "userma";
	}

	@RequestMapping("/studybatch")
	public String studybatch() {
		return "studybatch";
	}

	@RequestMapping("/jump/jumpuser")
	public String jumpuser() {
		return "jump/jumpuser";
	}

	@RequestMapping("clazzmanage")
	public String clazzmanage() {
		return "clazzmanage";
	}

	@RequestMapping("batchmanage")
	public String batchmanage() {
		return "batchmanage";
	}

	@RequestMapping("courseware")
	public String courseware() {
		return "courseware";
	}

	@RequestMapping(value = "/logout", produces = "text/html;charset=UTF-8")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "/login";
	}

	@RequestMapping(value = "/usered", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String usered(User_Table user_Table) {
		int i = manageService.updateUser(user_Table);
		return "1";
	}

	@RequestMapping(value = "/vpass", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String vpass(String id) {
		int i = manageService.vPass(id);
		return "1";
	}

	@RequestMapping(value = "/addClazz", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addClazz(Course course) {
		int i = manageService.addClazz(course);
		return "1";
	}

	@RequestMapping(value = "/add_ba_open", produces = "text/html;charset=UTF-8")
	public String add_ba_open(int id) {
		int i = manageService.baOpen(id);
		return "/jump/jumpbatch";
	}

	@RequestMapping(value = "/add_ba_close", produces = "text/html;charset=UTF-8")
	public String add_ba_close(int id) {
		int i = manageService.baClose(id);
		return "/jump/jumpbatch";
	}

	@RequestMapping(value = "/addBatch", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addBatch(String from, String to, String courseName, String courseText) {
		Date date = Date.valueOf(from);
		Date date2 = Date.valueOf(to);
		Study_Batch study_Batch = new Study_Batch();
		study_Batch.setStartTime(date);
		study_Batch.setEndTime(date2);
		study_Batch.setBatchName(courseName);
		study_Batch.setBatchText(courseText);
		int i = manageService.addBatch(study_Batch);
		return "1";
	}

	@RequestMapping("/userTable")
	public String searchInvList(Page page, HttpServletRequest request) throws UnsupportedEncodingException {
		Page p = page;
		int pageSize = 5;
		p.setPageSize(pageSize);
		int curPage = p.getCurrentPage();

		if (curPage == 0) {
			curPage = 1;
			p.setCurrentPage(curPage);
		}
		int startRow = page.getStartRow();

		if (!(p.getCurrentPage() == 0)) {
			startRow = getStartRowBycurrentPage(curPage, pageSize);
		}
		p.setStartRow(startRow);

		String queryCondition = null;
		if (page.getQueryCondition() != null) {
			queryCondition = page.getQueryCondition();
		}
		List<User_Table> user_Tables = getInvListByCondition(page);
		Integer totalCounts = manageService.searchTotalCountT(page);
		int totalPages = (totalCounts % pageSize == 0) ? (totalCounts / pageSize) : (totalCounts / pageSize + 1);
		p.setTotalPage(totalPages);
		page.setTotalRows(totalCounts);
		request.setAttribute("user_Tables", user_Tables);
		request.setAttribute("page", page);
		return "userma";
	}

	private List<User_Table> getInvListByCondition(Page page) {
		List<User_Table> user_Tables = null;
		if (page.getQueryCondition() == null) {
			user_Tables = manageService.searchInvListT(page);
			return user_Tables;
		}
		user_Tables = manageService.getInvBycondtionT(page);
		return user_Tables;
	}

	public int getStartRowBycurrentPage(int currentPage, int pageSize) {
		int startRow = 0;
		if (currentPage == 1) {
			return startRow = 0;
		}
		startRow = (currentPage - 1) * pageSize;
		return startRow;
	}

	@RequestMapping("/clazzTable")
	public String searchInvListC(Page page, HttpServletRequest request) throws UnsupportedEncodingException {
		Page p = page;
		int pageSize = 5;
		p.setPageSize(pageSize);
		int curPage = p.getCurrentPage();

		if (curPage == 0) {
			curPage = 1;
			p.setCurrentPage(curPage);
		}
		int startRow = page.getStartRow();

		if (!(p.getCurrentPage() == 0)) {
			startRow = getStartRowBycurrentPageC(curPage, pageSize);
		}
		p.setStartRow(startRow);

		String queryCondition = null;
		if (page.getQueryCondition() != null) {
			queryCondition = page.getQueryCondition();
		}
		List<Course> courses = getInvListByConditionC(page);
		Integer totalCounts = manageService.searchTotalCountC(page);
		int totalPages = (totalCounts % pageSize == 0) ? (totalCounts / pageSize) : (totalCounts / pageSize + 1);
		p.setTotalPage(totalPages);
		page.setTotalRows(totalCounts);
		request.setAttribute("courses", courses);
		request.setAttribute("page", page);
		return "clazzmanage";
	}

	private List<Course> getInvListByConditionC(Page page) {
		List<Course> courses = null;
		if (page.getQueryCondition() == null) {
			courses = manageService.searchInvListC(page);
			return courses;
		}
		courses = manageService.getInvBycondtionC(page);
		return courses;
	}

	public int getStartRowBycurrentPageC(int currentPage, int pageSize) {
		int startRow = 0;
		if (currentPage == 1) {
			return startRow = 0;
		}
		startRow = (currentPage - 1) * pageSize;
		return startRow;
	}

	@RequestMapping("/batchTable")
	public String searchInvListB(Page page, HttpServletRequest request) throws UnsupportedEncodingException {
		Page p = page;
		int pageSize = 5;
		p.setPageSize(pageSize);
		int curPage = p.getCurrentPage();

		if (curPage == 0) {
			curPage = 1;
			p.setCurrentPage(curPage);
		}
		int startRow = page.getStartRow();

		if (!(p.getCurrentPage() == 0)) {
			startRow = getStartRowBycurrentPageB(curPage, pageSize);
		}
		p.setStartRow(startRow);

		String queryCondition = null;
		if (page.getQueryCondition() != null) {
			queryCondition = page.getQueryCondition();
		}
		List<Study_Batch> study_Batchs = getInvListByConditionB(page);
		Integer totalCounts = manageService.searchTotalCountB(page);
		int totalPages = (totalCounts % pageSize == 0) ? (totalCounts / pageSize) : (totalCounts / pageSize + 1);
		p.setTotalPage(totalPages);
		page.setTotalRows(totalCounts);
		request.setAttribute("study_Batchs", study_Batchs);
		request.setAttribute("page", page);
		return "batchmanage";
	}

	private List<Study_Batch> getInvListByConditionB(Page page) {
		List<Study_Batch> study_Batchs = null;
		if (page.getQueryCondition() == null) {
			study_Batchs = manageService.searchInvListB(page);
			return study_Batchs;
		}
		study_Batchs = manageService.getInvBycondtionB(page);
		return study_Batchs;
	}

	public int getStartRowBycurrentPageB(int currentPage, int pageSize) {
		int startRow = 0;
		if (currentPage == 1) {
			return startRow = 0;
		}
		startRow = (currentPage - 1) * pageSize;
		return startRow;
	}

	@RequestMapping(value = "/getCourseByBatchId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Course> addBatch(int id) {
		List<String> string1 = new ArrayList<>();
		List<String> string2 = new ArrayList<>();
		List<Course> courses2 = manageService.getCourseByBatch2(id);
		if(courses2.size()==0) {
			List<Course> coursesall=manageService.getCourse();
			return coursesall;
		}else {
		List<Course> courses = manageService.getCourse();
		List<Course> coursesa = new ArrayList<>();
		for (Course coursex : courses) {
			string1.add(coursex.getCourseName());
		}

		for (Course coursexx : courses2) {
			string2.add(coursexx.getCourseName());
		}
		string1.removeAll(string2);
		if (string2.size() == 0) {
			return null;
		}
		for (int i = 0; i < courses.size(); i++) {
			for (int j = 0; j < string1.size(); j++) {
				if (courses.get(i).getCourseName() == string1.get(j) && courses.get(i).getCourseName() != null) {
					coursesa.add(courses.get(i));
					courses.remove(i);
				}
			}
		}

		courses.removeAll(courses2);
		return coursesa;
		}
	}

	@RequestMapping(value = "/getselectc", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Course> getselectc(int id) {
		List<Course> courses2 = manageService.getCourseByBatch2(id);
			return courses2;
	}
	
	@RequestMapping(value = "/getClazz", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Course> getClazz() {
		List<Course> courses2=manageService.getCourse();
		return courses2;
	}
	
	@RequestMapping(value = "/addbc", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addbc(String[] data,int id) {
		System.out.println(Arrays.toString(data));
		List<Batch_CourseKey> batch_CourseKeys=new ArrayList<>();
		for (int i = 0; i < data.length; i++) {
			Course courses2=manageService.getCourseByString(data[i]);
			Batch_CourseKey batch_CourseKey=new Batch_CourseKey();
			batch_CourseKey.setBatchId(id);
			batch_CourseKey.setCourseId(courses2.getCourseId());
			batch_CourseKeys.add(batch_CourseKey);
		}
		int i = manageService.addBC(batch_CourseKeys);
		return "1";
	}
}
