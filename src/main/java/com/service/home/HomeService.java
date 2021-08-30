package com.service.home;

import com.form.home.HomeForm;

/** 홈화면서비스. */
public interface HomeService {


    /**
     * 이름 목록 취득 처리.
     * @param form LoginForm.
     * @return boolean 처리결과.
     * @throws Exception 예외.
     * */
    HomeForm findNameList(HomeForm form) throws Exception;
    
    /**
     * 가격 취득 처리.
     * @param form LoginForm.
     * @return boolean 처리결과.
     * @throws Exception 예외.
     * */
    HomeForm findPrice(HomeForm form) throws Exception;
    
}
