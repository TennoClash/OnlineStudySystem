package oss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oss.entity.Courseware;
import oss.entity.Record_Join;
import oss.service.TestService;


@Controller
public class TestController {
@Autowired TestService testService;
	
	@RequestMapping(value = "/testQuery", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String isUserBatch() {
		int course[] = null;
		List<Courseware> coursewares = testService.queryCourseWare();
		List<Record_Join> recordjoin = testService.queryRecord();
		for(int i = 0 ; i < coursewares.size() ; i++) {
			for(int k = 0 ; k < recordjoin.size() ; k++) {
				if(coursewares.get(i).getCourseId()==recordjoin.get(k).getCourseId()) {
					System.out.println(recordjoin.get(k).getUserId()+"+"+recordjoin.get(k).getViewTime()+"+"+coursewares.get(i).getVideoTime());
					double a=Integer.parseInt(recordjoin.get(k).getViewTime());
					double b=Integer.parseInt(coursewares.get(i).getVideoTime());
					System.out.println(a/b);
				}
				
				}

			}
		
		return "1";
	}
	
}
