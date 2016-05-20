package com.capgemini.wdapp.vendor.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.capgemini.wdapp.util.HttpRequest;
import com.capgemini.wdapp.util.SignUtil;

public class WeChatPayUtil {
	public static String UNIFIED_ORDER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
	
    public static  JSONObject getPrepayJson(String xml){
		String request_url = UNIFIED_ORDER_URL;
		String res = HttpRequest.sendPost(request_url, xml);
    		
        try {
            //读取响应
            JSONObject o =SignUtil.xml2JSON(res);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            
        }
        return null;
    }
    public static  String getPrepayid(String xml){
        try {
            JSONObject jo=getPrepayJson(xml);
            JSONObject element=jo.getJSONObject("xml");
            String prepayid=((JSONArray)element.get("prepay_id")).get(0).toString();
            return prepayid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public  String orderNum(){
        String chars = "0123456789";
        String order = System.currentTimeMillis()+"";
        String res = "";
        for (int i = 0; i < 19; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        order+=res;
        return order;
    }

	

	
}
