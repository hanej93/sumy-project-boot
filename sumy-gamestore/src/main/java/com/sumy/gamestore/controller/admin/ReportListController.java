package com.sumy.gamestore.controller.admin;

import com.sumy.gamestore.service.ReportListService;
import com.sumy.gamestore.vo.PagingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ReportListController {
	
	private final ReportListService reportListService;
	
	@GetMapping("/report/list")
	public String showReport(PagingVO vo, Model model
			, @RequestParam(value="nowPage", required=false)String nowPage
			, @RequestParam(value="cntPerPage", required=false)String cntPerPage) {
		
		int total = reportListService.getCountForPaging(vo);
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "5";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) { 
			cntPerPage = "5";
		}
		String reportReadYn = vo.getReportReadYn();
		vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage), vo.getKeyword());
		vo.setReportReadYn(reportReadYn);
		model.addAttribute("paging", vo);
		model.addAttribute("viewAll", reportListService.findList(vo));
		
		return "admin/report_list";
	}
	
//	@GetMapping("/news/update")
//	public String updateNews() {
//		
//		return "admin/news_update";
//	}
	
}
