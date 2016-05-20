package com.capgemini.wdapp.vendor.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.util.StringUtils;

import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.util.HttpRequest;
import com.capgemini.wdapp.util.JsonUtil;

public class WeChatApiUtil {
	private final static String WECHAT_OPEN_ROOT = "https://api.weixin.qq.com/cgi-bin/";
	private final static String URL_TOKEN = WECHAT_OPEN_ROOT + "token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private final static String URL_TICKET = WECHAT_OPEN_ROOT +"ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	private final static String URL_MENU_CREATE = WECHAT_OPEN_ROOT +"menu/create?access_token=ACCESS_TOKEN";
	private final static String URL_CONDITION_MENU_CREATE = WECHAT_OPEN_ROOT +"menu/addconditional?access_token=ACCESS_TOKEN";

	private final static String URL_MENU_GET = WECHAT_OPEN_ROOT +"menu/get?access_token=ACCESS_TOKEN";
	
	private final static String URL_TEMPLATE_SET_INDUSTRY = WECHAT_OPEN_ROOT +"template/api_set_industry?access_token=ACCESS_TOKEN";
	private final static String URL_TEMPLATE_ADD_TEMPLATE_ID = WECHAT_OPEN_ROOT +"template/api_add_template?access_token=ACCESS_TOKEN";
	private final static String URL_MESSAGE_TEMPLATE_SEND = WECHAT_OPEN_ROOT +"message/template/send?access_token=ACCESS_TOKEN";
	private final static String URL_WEB_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private final static String URL_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private final static String URL_GET_CODE = "http://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_urI=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	public static String getTimestamp() {
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		return timestamp;
	}

	public static String getNonstr() {
		return UUID.randomUUID().toString();
	}

	public static String getAccesstoken(String appid, String appsecret) {
		String request_url = URL_TOKEN.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		String access_token = "";
		JSONObject result = HttpRequest.sendRequest(request_url, "GET");
		if(StringUtils.isEmpty(result)) {
			return access_token;
		}
		access_token = result.getString("access_token");
		return access_token;
	}

	public static String getJSTicket(String accesstoken) {
		String request_url = URL_TICKET.replace("ACCESS_TOKEN", accesstoken);
		String jsapi_ticket = "";
		JSONObject result = HttpRequest.sendRequest(request_url, "GET");
		if(StringUtils.isEmpty(result)) {
			return jsapi_ticket;
		}
		jsapi_ticket = result.getString("ticket");
		return jsapi_ticket;
	}

	public static JSONObject getWebAccessTokenAndOpenID(String appid, String appsecret,
			String code) {
		String request_url = URL_WEB_TOKEN.replace("APPID", appid)
				.replace("SECRET", appsecret).replace("CODE", code);
		JSONObject jo = null;
		jo = HttpRequest.sendRequest(request_url, "GET");
		return jo;
	}
	
	public static JSONObject getUserInfo(String webToken, String openId){
		JSONObject jo = null;
		String request_url = URL_USER_INFO.replace("ACCESS_TOKEN", webToken).replace("OPENID", openId);
		
		jo = HttpRequest.sendRequest(request_url, "GET");
		return jo;
	}
	
	public static String requestToWeixin(String appid, String appsecret, Map<String, Object> params) {
		//String callbackUrl = "http://hzmobility.ngrok.cc/WDApp/api/wechat/callback";server.root
		String callbackUrl = ConfigUtil.getPropertyValue("server.root") + "api/wechat/callback";
		int index = 0;
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			callbackUrl += (index == 0 ? "?" : "&") + entry.getKey() + "=" + entry.getValue();
			index ++;
		}
		try {
			callbackUrl = URLEncoder.encode(callbackUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			return null;
		}
		String request_url = URL_GET_CODE.replace("APPID", appid).replace("SCOPE", "snsapi_userinfo").replace("REDIRECT_URI", callbackUrl).replace("STATE", "");
		return request_url;
	}
	
	public static String setButtons(String accessToken, Buttons buttons) {
		String request_url = URL_MENU_CREATE.replace("ACCESS_TOKEN", accessToken);
		String buttonsJson = JsonUtil.toJson(buttons);
		String res = HttpRequest.sendPost(request_url, buttonsJson);
		//RESTHttpClient client = new RESTHttpClient("");
		//String res = "";
//		try {
//			res = client.doCommonPost(request_url, buttonsJson);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return res;
		
	}
	
	public static String setConditionButtons(String accessToken, Buttons buttons) {
		String request_url = URL_CONDITION_MENU_CREATE.replace("ACCESS_TOKEN", accessToken);
		String buttonsJson = JsonUtil.toJson(buttons);
		String res = HttpRequest.sendPost(request_url, buttonsJson);
		//RESTHttpClient client = new RESTHttpClient("");
		//String res = "";
//		try {
//			res = client.doCommonPost(request_url, buttonsJson);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return res;
		
	}

	public static String setIndustry(String accessToken, Industry industry) {
		String request_url = URL_TEMPLATE_SET_INDUSTRY.replace("ACCESS_TOKEN", accessToken);
		String industryJson = JsonUtil.toJson(industry);
		String res = HttpRequest.sendPost(request_url, industryJson);
		return res;
	}
	
	public static boolean isApiSuccess(String response) {
		JSONObject json = new JSONObject(response);
    	if (json != null && json.getInt("errcode") == 0) {
    		return true;
    	} else {
    		return false;
    	}
	}

	

	
}
