package com.web.home;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.form.home.HomeForm;
import com.service.home.HomeService;

/** 홈 컨트롤러. */
@Controller
@EnableAutoConfiguration
public class HomeController {
	
	@Autowired
	private HomeService homservice;

    /**
     * GET리퀘스트(초기화면 표시용).
     *  @param model Model.
     *  @return String 화면명.
     *  @throws Exception 예외처리.
     * */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String init(@ModelAttribute
    		final HomeForm homeForm,
    		final Model model) throws Exception {
		
		homeForm.setType("");
		homeForm.setNameVal("");
		homeForm.setPrice("");
		List<String> initList = new ArrayList<>();
		homeForm.setNameList(initList);
		
		model.addAttribute(homeForm);
		model.addAttribute("nameList",initList);
        return "Home";
    }
	
	/**
     * POST리퀘스트(토큰 취득/목록 조회).
     *  @param model Model.
     *  @return String 화면명.
     *  @throws Exception 예외처리.
     * */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    public String getList(@ModelAttribute
    		final HomeForm homeForm,
    		final Model model) throws Exception {

		HomeForm newForm = homservice.findNameList(homeForm);
		model.addAttribute(newForm);
		model.addAttribute("nameList",newForm.getNameList());
		return "Home";
	}
	
	/**
     * POST리퀘스트(가격 조회).
     *  @param model Model.
     *  @return String 화면명.
     *  @throws Exception 예외처리.
     * */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
    public String getPrice(@ModelAttribute
    		final HomeForm homeForm,
    		final Model model) throws Exception {

		HomeForm newForm = homservice.findPrice(homeForm);
		model.addAttribute(newForm);
		return "Home";
	}
}
