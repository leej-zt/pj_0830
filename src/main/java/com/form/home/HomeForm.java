package com.form.home;

import java.util.List;

/** 홈화면form. */
public class HomeForm {

    /** 분류. */
    private String type;

    /** 목록. */
    private List<String> nameList;

    /** 이름. */
    private String nameVal;

    /** 가격. */
    private String price;

    /** 토큰. */
    private String token;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getNameList() {
		return nameList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public String getNameVal() {
		return nameVal;
	}

	public void setNameVal(String nameVal) {
		this.nameVal = nameVal;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
  
}
