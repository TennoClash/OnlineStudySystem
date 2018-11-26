package oss.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import oss.entity.Add_Test;
import oss.entity.Courseware;
import oss.entity.Page;
import oss.entity.Record_Join;
import oss.entity.Test_Table;
import oss.entity.User_Table;
import oss.service.TestService;

@Controller
public class TestController {
	@Autowired
	TestService testService;

	@RequestMapping(value = "/testQuery", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<User_Table> testQuery() {
		List<User_Table> user_Tables=Qualifications();
		return user_Tables;
	}
	

	
	@RequestMapping("/info")
	public void info(HttpServletRequest request, HttpServletResponse response){
		List<User_Table> user_Tables=Qualifications();
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("考试资格表");
		Row rowm = sheet.createRow(0);
		Cell cellr = rowm.createCell(0);
		cellr.setCellValue("2016-2017学年度java培训");
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		
		Row row = sheet.createRow(1);
			Cell cell = row.createCell(0);
			cell.setCellValue("学员姓名");
			cell = row.createCell(1);
			cell.setCellValue("学员帐号");
			cell = row.createCell(2);
			cell.setCellValue("学习完成状况");
			cell = row.createCell(3);
			cell.setCellValue("是否允许考试");
			 DecimalFormat df = new DecimalFormat("0%");
		for (int ii=0;ii<user_Tables.size();ii++)
        {
			System.out.println(user_Tables.get(ii).getRealName());
			row =sheet.createRow(ii+2);
			row.createCell(0).setCellValue(user_Tables.get(ii).getRealName());
			row.createCell(1).setCellValue(user_Tables.get(ii).getUserName());
			row.createCell(2).setCellValue(df.format(Double.parseDouble(user_Tables.get(ii).getEmail())));
			row.createCell(3).setCellValue("可进行考试");
        }

		
		OutputStream fos = null;
		try {
			fos = response.getOutputStream();
			String userAgent = request.getHeader("USER-AGENT");
			String fileName = "test";
			try {
				
					fileName = URLEncoder.encode(fileName, "utf8");

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
 
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	public List<User_Table> Qualifications(){
		List<Integer> course=new ArrayList<>();
		List<String> spro=new ArrayList<>();
		
		int n=0;
		List<Courseware> coursewares = testService.queryCourseWare();
		List<Record_Join> recordjoin = testService.queryRecord();
		for (int i = 0; i < coursewares.size(); i++) {
			for (int k = 0; k < recordjoin.size(); k++) {
				if (coursewares.get(i).getCourseId() == recordjoin.get(k).getCourseId()) {
					System.out.println(recordjoin.get(k).getUserId() + "+" + recordjoin.get(k).getViewTime() + "+"
							+ coursewares.get(i).getVideoTime());
					double a = Integer.parseInt(recordjoin.get(k).getViewTime());
					double b = Integer.parseInt(coursewares.get(i).getVideoTime());
					double c = a / b;
					if (c > 0.8) {
						System.out.println(recordjoin.get(k).getUserId());
						NumberFormat nf = NumberFormat.getNumberInstance();
						nf.setMaximumFractionDigits(2);
						System.out.println("fromat"+nf.format(c));
						spro.add(nf.format(c));
						course.add(recordjoin.get(k).getUserId());
					}
				}
			}

		}
		List<User_Table> user_Tables = testService.queryUserById(course);
		for(int ii=0;ii<spro.size();ii++) {
			user_Tables.get(ii).setEmail(spro.get(ii));
		}
		
		return user_Tables;
	}
	
	@RequestMapping(value = "/testUpload", produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String course_info(String ListSrt) {
		System.out.println(ListSrt);
			List<Add_Test> add_Tests = JSONObject.parseArray(ListSrt,Add_Test.class);			
			//int i=upDateService.Course_Update(courses);
			List<Test_Table> test_Tables=new ArrayList<>();
			Date date = new Date();
			String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			int batchId=testService.findBatch();
			for(int i=0;i<add_Tests.size();i++) {
				Test_Table tables=new Test_Table();
				tables.setUserId(add_Tests.get(i).getUserId());
				tables.setCourseId(add_Tests.get(i).getCourseId());
				tables.setScore(add_Tests.get(i).getScore());
				tables.setTestType(1);
				tables.setTestTime(nowTime);
				tables.setBatchId(batchId);
				test_Tables.add(tables);
			}
			int i=testService.insretTest(test_Tables);
			return String.valueOf(test_Tables.size());		
	}
	
	@RequestMapping("/testTable")
	public String searchInvListTest(Page page, HttpServletRequest request) throws UnsupportedEncodingException {
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
		List<Test_Table> test_Tables = getInvListByCondition(page);
		Integer totalCounts = testService.searchTotalCountTest(page);
		int totalPages = (totalCounts % pageSize == 0) ? (totalCounts / pageSize) : (totalCounts / pageSize + 1);
		p.setTotalPage(totalPages);
		page.setTotalRows(totalCounts);
		request.setAttribute("test_Tables", test_Tables);
		request.setAttribute("page", page);
		return "testmanage";
	}
	
	@RequestMapping("/testTable2")
	public String searchInvListTest2(Page page, HttpServletRequest request) throws UnsupportedEncodingException {
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
		List<Test_Table> test_Tables = getInvListByCondition(page);
		Integer totalCounts = testService.searchTotalCountTest(page);
		int totalPages = (totalCounts % pageSize == 0) ? (totalCounts / pageSize) : (totalCounts / pageSize + 1);
		p.setTotalPage(totalPages);
		page.setTotalRows(totalCounts);
		request.setAttribute("test_Tables", test_Tables);
		request.setAttribute("page", page);
		return "stest";
	}

	private  List<Test_Table> getInvListByCondition(Page page) {
		List<Test_Table> test_Tables = null;
		if (page.getQueryCondition() == null) {
			test_Tables = testService.searchInvListTest(page);
			return test_Tables;
		}
		test_Tables = testService.getInvBycondtionTest(page);
		return test_Tables;
	}


	public int getStartRowBycurrentPage(int currentPage, int pageSize) {
		int startRow = 0;
		if (currentPage == 1) {
			return startRow = 0;
		}
		startRow = (currentPage - 1) * pageSize;
		return startRow;
	}
	
	
}
