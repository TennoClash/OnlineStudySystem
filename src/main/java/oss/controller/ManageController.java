package oss.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jws.soap.SOAPBinding.Style;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oss.entity.Course;
import oss.entity.Page;
import oss.entity.Study_Batch;
import oss.entity.User_Table;
import oss.service.ManageService;




@Controller
public class ManageController {
	@Autowired private ManageService manageService;
	
	@RequestMapping("/studycourse")
	public String studycourse() {
		return "studycourse";
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
		int i=manageService.vPass(id);
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
	
	@RequestMapping(value = "/getClazz", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Course> getClazz() {
		List<Course> courses=manageService.getCourse();
		return courses;
	}
	
	@RequestMapping(value = "/addBatch", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addBatch(String from,String to,String courseName,String courseText) {
		Date date=Date.valueOf(from);
		Date date2=Date.valueOf(to);
		Study_Batch study_Batch=new Study_Batch();
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

		private  List<User_Table> getInvListByCondition(Page page) {
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
}
