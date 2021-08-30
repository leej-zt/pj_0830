package com.service.home;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.form.home.HomeForm;

/** 홈화면서비스. */
@Service
public class HomeServiceImpl implements HomeService {
	
	public static final String FRUITURL = "http://fruit.api.postype.net/";
	public static final String VEGEURL = "http://vegetable.api.postype.net/";

    @Override
    public final HomeForm findNameList(final HomeForm form)
            throws Exception {

    	HomeForm returnForm = new HomeForm();
    	List<String> nameList = new ArrayList<>();
        try {
        	
        	if(form.getType().equals("1")) {
        		// 과일가게 access token 발급
        		StringBuffer resultSb = callAPI(FRUITURL+"token", "", false, false);

        		// 토큰 취득 - json에서 토큰 값만 빼오는 처리
        		JSONParser parser = new JSONParser();
        		Object obj = parser.parse(resultSb.toString());
        		JSONObject jsonObj = (JSONObject)obj;
        		String token = (String) jsonObj.get("accessToken");
        		
        		// 과일 리스트 취득
        		resultSb = callAPI(FRUITURL+"product", token, true, false);
        		
        		// 리스트로 변환
        		nameList = getList(resultSb);
        		returnForm.setToken(token);
        		
        	} else if(form.getType().equals("2")) {
        		// 채소가게 access token 발급
        		StringBuffer resultSb = callAPI(VEGEURL+"token", "", false, true);

        		// 토큰 취득 - 쿠키 정보에서 토큰 값만 빼오는 처리
        		String token = resultSb.toString().split("=")[1];
        		token = token.split(";")[0];
        		
        		// 채소 리스트 취득
        		resultSb = callAPI(VEGEURL+"item", token, true, false);
        		
        		// 리스트로 변환
        		nameList = getList(resultSb);
        		returnForm.setToken(token);
        	}

        } catch (Exception e) {
            return returnForm;
        }
        returnForm.setNameList(nameList);
        return returnForm;

    }
    
	@Override
	public HomeForm findPrice(HomeForm form) throws Exception {
		
		HomeForm returnForm = new HomeForm();
		
		String price = "";
		String token = form.getToken();
		String name = form.getNameVal();
		name = URLEncoder.encode(name, "UTF-8");
		
		try {
			if(form.getType().equals("1")) {
				// 과일 가격 취득
				StringBuffer resultSb = callAPI(FRUITURL+"product?name="+name, token, true, false);
				
				// price만 반환
				price = getList(resultSb).get(1).split(":")[1];
			} else if(form.getType().equals("2")) {
				// 채소 가격 취득
				StringBuffer resultSb = callAPI(VEGEURL+"item?name="+name, token, true, false);
				
				// price만 반환
				price = getList(resultSb).get(1).split(":")[1];
			}
			
		} catch(Exception e) {
			return returnForm;
		}
		
		returnForm.setPrice(price);
		return returnForm;
	}

    
	/**
     * 외부API 통신처리.
     * @param String 통신용 URL.
     * @param String 토큰.
     * @param boolean 토큰 설정용 플래그.
     * @param boolean 채소 토큰 취득용 플래그.
     * @return StringBuffer 처리결과.
     * @throws Exception 예외처리.
     * */
    private StringBuffer callAPI(String apiUrl, String token, boolean flg, boolean flg2) {
    	
    	StringBuffer response = new StringBuffer();
    	
        try {
        	URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
	    	if(flg) {
	    		con.setRequestProperty("Authorization", token);
	    	}
	    	
            int responseCode = con.getResponseCode();
            BufferedReader br;
            
            if(responseCode == 200) {
            	// 정상일 경우
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
                if(flg2) {
                	// 채소의 경우, header에서 토큰 취득
                	response.append(con.getHeaderFields().get("Set-Cookie"));
                	br.close();
                	return response;
                }
            } else {
            	// 에러일 경우
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            
            br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return response;
    }
    
    // 결과값에 대한 리스트 변환처리
    private List<String> getList(StringBuffer sb) {
    	String str = sb.toString();
    	
    	// json 내부의 특수문자 제거
    	str = str.replace("[", "");
    	str = str.replace("]", "");
    	str = str.replace("\"", "");
    	str = str.replace("{", "");
    	str = str.replace("}", "");
    	String[] strArr = str.split(",");
    	
    	List<String> returnList = new ArrayList<>();
    	for(String val : strArr) {
    		returnList.add(val);
    	}
    	return returnList;
    }

}
