package oss.controller;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import oss.entity.Batch_CourseKey;
import oss.entity.Course;
import oss.entity.Courseware;
import oss.entity.Page;
import oss.entity.Record_Join;
import oss.entity.Study_Batch;
import oss.entity.Study_Record;
import oss.entity.User_BatchKey;
import oss.service.CourseWareService;
import oss.service.ManageService;
import oss.util.FetchFrame;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;


@Controller
public class CourseWareController {
@Autowired CourseWareService courseWareService;
@Autowired ManageService manageService;

@RequestMapping(value = "/editCourseWare", produces = "text/html;charset=UTF-8")
@ResponseBody
public String editCourseWare(int id,String name,String text,int select1) {
	Courseware courseware=new Courseware();
	courseware.setCourseId(select1);
	courseware.setCoursewareId(id);
	courseware.setCoursewareName(name);
	courseware.setCoursewareText(text);
	int i=courseWareService.updateCourseWare(courseware);
	return "1";
}

	@RequestMapping("/upload_submit")
	@ResponseBody
	public String fileUpload(@RequestParam("saveFiles") MultipartFile file,HttpServletRequest request)
			throws IOException {
		String filename = file.getOriginalFilename();
		Courseware courseware=new Courseware();
		courseware.setCoursewareName(filename.substring(0,filename.lastIndexOf(".")));
		//String path = request.getServletContext().getRealPath("/video/")+ filename;
		String path = "H:/ossMedia/video/"+ filename;
		File f = new File(path);
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		file.transferTo(f);
		courseware.setVideoPath(filename);
		/* 获取视频信息*/
		MultimediaObject instance = new MultimediaObject(f);
        try {
			MultimediaInfo result = instance.getInfo();
			courseware.setVideoTime(Long.toString(result.getDuration()/1000));
		} catch (EncoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        /*获取缩略图*/
        try {
			//FetchFrame.fetchFrame(path, request.getServletContext().getRealPath("/fetch/")+filename.substring(0,filename.lastIndexOf("."))+".jpg");
        	FetchFrame.fetchFrame(path, "H:/ossMedia/fetch/"+filename.substring(0,filename.lastIndexOf("."))+".jpg");
        	courseware.setPicPath(filename.substring(0,filename.lastIndexOf("."))+".jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int i=courseWareService.addCourseWare(courseware);
		return String.valueOf(i);
	}
	
	/*课件分页*/
	@RequestMapping("/courseWareTable")
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
		List<Courseware> coursewares = getInvListByConditionC(page);
		Integer totalCounts = courseWareService.searchTotalCountC(page);
		int totalPages = (totalCounts % pageSize == 0) ? (totalCounts / pageSize) : (totalCounts / pageSize + 1);
		p.setTotalPage(totalPages);
		page.setTotalRows(totalCounts);
		request.setAttribute("coursewares", coursewares);
		request.setAttribute("page", page);
		return "courseware";
	}

	private List<Courseware> getInvListByConditionC(Page page) {
		List<Courseware> coursewares = null;
		if (page.getQueryCondition() == null) {
			coursewares = courseWareService.searchInvListC(page);
			return coursewares;
		}
		coursewares = courseWareService.getInvBycondtionC(page);
		return coursewares;
	}


	public int getStartRowBycurrentPageC(int currentPage, int pageSize) {
		int startRow = 0;
		if (currentPage == 1) {
			return startRow = 0;
		}
		startRow = (currentPage - 1) * pageSize;
		return startRow;
	}
	
	
	
	
	 @RequestMapping("/batchTable2")
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
			return "studybatch";
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
		
		
		@RequestMapping(value = "/isUserBatch", produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String isUserBatch(int uid,int bid) {
			User_BatchKey user_BatchKey=new User_BatchKey();
			user_BatchKey.setBatchId(bid);
			user_BatchKey.setUserId(uid);
			User_BatchKey user_BatchKey2=courseWareService.isUserBatch(user_BatchKey);
			if(user_BatchKey2!=null) {
			}else {
				int i= courseWareService.addUserBatch(user_BatchKey);
			}
			return "1";
		}
		
		@RequestMapping(value = "/getBatchCourse", produces = "application/json;charset=UTF-8")
		@ResponseBody
		public List<Course> getBatchCourse(int bid) {
			List<Course> courses=courseWareService.getBatchCourse(bid);
			return courses;
		}
		
		@RequestMapping(value = "/getWare", produces = "application/json;charset=UTF-8")
		@ResponseBody
		public List<Courseware> getWare(int id) {
			List<Courseware> coursewares=courseWareService.getCourseWareByCourseId(id);
			return coursewares;
		}
		
		@RequestMapping(value = "/recordChange", produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String recordChange(int userid,int batchId,int coursewareId,String viewTime) {
		Study_Record study_Record=new Study_Record();
		study_Record.setBatchId(batchId);
		study_Record.setCoursewareId(coursewareId);
		study_Record.setUserId(userid);
		study_Record.setViewTime(viewTime);
		Study_Record study_Records=courseWareService.isRecordExist(study_Record);
				if(study_Records == null) {
					int i = courseWareService.upCurrentTime(study_Record);
				}else {
					study_Record.setRecordId(study_Records.getRecordId());
					int i = courseWareService.upCurrentTime2(study_Record);
				}
			return "1";
		}
		
		@RequestMapping(value = "/recordResume", produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String recordResume(int userid,int batchId,int coursewareId) {
		Study_Record study_Record=new Study_Record();
		study_Record.setBatchId(batchId);
		study_Record.setCoursewareId(coursewareId);
		study_Record.setUserId(userid);
		Study_Record study_Records=courseWareService.isRecordExist(study_Record);
				if(study_Records == null) {
					return "0";
				}else {
					return study_Records.getViewTime();
				}
		}
		
		@RequestMapping("/RecordTableAdmin")
		public String searchInvListRAdmin(Page page, HttpServletRequest request) throws UnsupportedEncodingException {
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
			List<Record_Join> record_Joins = getInvListByConditionR(page);
			Integer totalCounts = courseWareService.searchTotalCountR(page);
			int totalPages = (totalCounts % pageSize == 0) ? (totalCounts / pageSize) : (totalCounts / pageSize + 1);
			p.setTotalPage(totalPages);
			page.setTotalRows(totalCounts);
			request.setAttribute("record_Joins", record_Joins);
			request.setAttribute("page", page);
			return "adminrecord";
		}
		
		@RequestMapping("/RecordTable")
		public String searchInvListR(Page page, HttpServletRequest request) throws UnsupportedEncodingException {
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
			List<Record_Join> record_Joins = getInvListByConditionR(page);
			Integer totalCounts = courseWareService.searchTotalCountR(page);
			int totalPages = (totalCounts % pageSize == 0) ? (totalCounts / pageSize) : (totalCounts / pageSize + 1);
			p.setTotalPage(totalPages);
			page.setTotalRows(totalCounts);
			request.setAttribute("record_Joins", record_Joins);
			request.setAttribute("page", page);
			return "srecord";
		}

		private List<Record_Join> getInvListByConditionR(Page page) {
			List<Record_Join> record_Joins = null;
			if (page.getQueryCondition() == null) {
				record_Joins = courseWareService.searchInvListR(page);
				return record_Joins;
			}
			record_Joins = courseWareService.getInvBycondtionR(page);
			return record_Joins;
		}


		public int getStartRowBycurrentPageR(int currentPage, int pageSize) {
			int startRow = 0;
			if (currentPage == 1) {
				return startRow = 0;
			}
			startRow = (currentPage - 1) * pageSize;
			return startRow;
		}
}
