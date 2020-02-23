//package com.big.tree.server;
//
//import com.dwd.example.dto.params.WeekReportDTO;
//import com.dwd.example.provider.WeekReportProvider;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * Description
// * Author ayt  on
// */
//@Component
//public class TestConsumer {
//	@Resource
//	WeekReportProvider weekReportProvider;
//
//	public List<String> queryWeek() {
//		WeekReportDTO weekReportDTO = new WeekReportDTO();
//		List<String> projectName = weekReportProvider.getAllProjectName(weekReportDTO);
//		System.out.println(projectName.toString());
//		return projectName;
//	}
//}
