package com.capgemini.wdapp.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.wdapp.common.JsonParamObj;
import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.model.Shop;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IOrderService;
import com.capgemini.wdapp.service.IPushService;
import com.capgemini.wdapp.service.IShopService;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.util.SignUtil;
import com.capgemini.wdapp.vendor.wechat.WeChatApiUtil;
import com.capgemini.wdapp.vendor.wechat.WeChatPayUtil;
import com.capgemini.wdapp.vendor.wechat.WechatConstants;



@Controller
@RequestMapping("/api/jssdk")
public class WechatJssdkController {
	@Autowired
    private IShopService shopService;
	@Autowired
    private IOrderService orderService;
	@Autowired
    private IPushService pushService;
	@RequestMapping(value = "/userAuthenticate", method = RequestMethod.GET)
	public @ResponseBody WDResponse getUserAuthenticate(HttpServletRequest request, @RequestParam String location,int shopId) {
		Shop shop = shopService.getShopById(shopId);
		
		Map<String,String> resultMap = new HashMap<String,String>();
		String timestamp = WeChatApiUtil.getTimestamp();
		System.out.println("timestamp is " + timestamp);
		// 随机数
		String nonce = WeChatApiUtil.getNonstr();
		System.out.println("nonce is " + nonce);
//		String jsurl = request.getRequestURL().toString();
		String jsurl = location;
		System.out.println("location is " + location);
		String access_token = WeChatApiUtil.getAccesstoken(shop.getWeChatAppKey(), shop.getWeChatAppSecret());
		System.out.println("access_token is " + access_token);
		String jsapi_ticket = WeChatApiUtil.getJSTicket(access_token);
		System.out.println("jsapi_ticket is " + jsapi_ticket);
		String signature = SignUtil.getSignature(jsapi_ticket, timestamp, nonce, jsurl).toLowerCase();
		System.out.println("signature is " + signature);
		
		resultMap.put("debug", WDConstant.DEBUG);
		resultMap.put("appId", shop.getWeChatAppKey());
		resultMap.put("timestamp", timestamp);
		resultMap.put("nonceStr", nonce);
		resultMap.put("signature", signature);
		
		return ResponseUtil.apiSuccess(resultMap, "add goods successful");
	}
	
//	@RequestMapping(value = "/getPrepay", method = RequestMethod.GET)
//	public @ResponseBody WDResponse getWebTokenAndOpenId(HttpServletRequest request, @RequestParam String code) {
//		JSONObject jo = WeChatApiUtil.getWebAccessTokenAndOpenID(WDConstant.APPID, WDConstant.SECRET, code);
//		return ResponseUtil.apiSuccess(jo, "get openid successful");
//	}
		
	
	
	@RequestMapping(value = "/getPrePay", method = RequestMethod.POST)
	public @ResponseBody WDResponse getPrePay(@RequestBody JsonParamObj query) {
        try{
        	if (StringUtils.isEmpty(query)) {
    			return ResponseUtil.apiError("1001", "缺少必要参数");
    		}
    		if (StringUtils.isEmpty(query.getOpenId())) {
    			return ResponseUtil.apiError("1002", "openId is null");
    		}
    		if (StringUtils.isEmpty(query.getShopId())) {
    			return ResponseUtil.apiError("1003", "shopId is null");
    		}
    		if (StringUtils.isEmpty(query.getOrderCode())) {
    			return ResponseUtil.apiError("1004", "orderCode is null");
    		}
		//获取prepayid
        Map<String ,String > map=new HashMap<String,String>();
         String nonceStr=UUID.randomUUID().toString().substring(0, 32);
        //oauthService.shareFactory(request);
         Shop shop = shopService.getShopById(query.getShopId());
         OrderMaster order = orderService.getOrderByOrderCode(query.getOrderCode());
         if (shop == null || order == null) {
        	 return ResponseUtil.apiSuccess("1001", "parameter error");
         }
         if (order.getStatus() != WDConstant.ORDERMASTER_STATUS_BOOKED) {
        	 return ResponseUtil.apiError("1002", "The order hava a invalid status");
         }
        String appid= shop.getWeChatAppKey();
         long timestamp = System.currentTimeMillis() / 1000;
        map.put("appid", appid );
        map.put("mch_id", WechatConstants.WECHAT_PAY_PARAM_MCH_ID);
        map.put("nonce_str",nonceStr);
        map.put("body",  order.getOrderCode());
        map.put("out_trade_no", order.getOrderCode());
        map.put("total_fee", "1");
        map.put("spbill_create_ip","10.61.213.151");
        String notify_url = ConfigUtil.getPropertyValue("server.root") + "api/jssdk/callback";
        map.put("notify_url", notify_url);
        map.put("trade_type", "JSAPI");
        map.put("openid", query.getOpenId());
        String paySign=SignUtil.getPayCustomSign(map,WechatConstants.WECHAT_PAY_API_KEY);
        map.put("sign",paySign);
        String xml=    SignUtil.ArrayToXml(map);
        String prepayid= WeChatPayUtil.getPrepayid(xml);
       //封装h5页面调用参数
        Map<String ,String > signMap=new HashMap<String ,String >();
        signMap.put("appId", appid);
        signMap.put("timeStamp", timestamp+"");
        signMap.put("package", "prepay_id="+prepayid);
        signMap.put("signType", "MD5");
        signMap.put("nonceStr", nonceStr);
        String paySign2=SignUtil.getPayCustomSign(signMap,WechatConstants.WECHAT_PAY_API_KEY);
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("paytimestamp", String.valueOf(timestamp));
        resultMap.put("paypackage", "prepay_id="+prepayid);
        resultMap.put("paynonceStr", nonceStr);
        resultMap.put("paysignType", "MD5");
        resultMap.put("paySign",paySign2);
        
        return ResponseUtil.apiSuccess(resultMap, "successful");
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
	
	@RequestMapping(value = "/callback", method = RequestMethod.POST)
    public String callback(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("~~~~~~~~~~~~~~~~！！！！！！！！我进入callback了！！！！！！！！！！！~~~~~~~~~");
		try{
		InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
        Map  map = SignUtil.parsePayCallback(result);
        if (map.containsKey("result_code")) {
        	String result_code = map.get("result_code").toString();
        	if (result_code.equals("[SUCCESS]")) {
        		String orderCode = map.get("out_trade_no").toString();
        		if (!StringUtils.isEmpty(orderCode)) {
        			orderCode = orderCode.substring(1, orderCode.length() -1);
        			orderService.orderPay(orderCode);
        			return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        		}
        	}
        }
		}catch(Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
}
