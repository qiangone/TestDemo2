package com.capgemini.wdapp.common;

/**
 * 
 * functional description： constants
 * @author  zhihuang@capgemini.com
 * @created Dec 16, 2015 2:38:12 PM
 */
public class WDConstant {
    
//    public static final 
	
	public static final String DEBUG = "false";
	public static final String GRANT_TYPE = "client_credential";
	public static final String SECRET = "7030c0a40c01892e7a488979214685f9";
	public static final String DOMAIN = "http://hzmobility.ngrok.cc/weidian";

	//public static final String APPID = "wxbae2eeab5381c819";
	//public static final String APPSECRET = "7030c0a40c01892e7a488979214685f9";
	
	

	public static final String SHOP_LOGO_IMAGE = "/image/shop/logoImage/";
	public static final String SHOP_BACKGROUND_IMAGE = "/image/shop/bcgImage/";
	public static final String GOODS_IMAGE = "/image/goods/";
	
	public static final String ORDER_AFTER_SALE_IMAGE = "/image/order/after_sale/";
	
	public static final int GOODS_NOT_ON_SALE= 0;//0: 没有上架
	public static final int GOODS_ON_SALE= 1;// 1:上架出售中
	
	
	// Order Master status
	public static final int ORDERMASTER_STATUS_BOOKED = 0;//买家已下单
	public static final int ORDERMASTER_STATUS_CANCELED = 1;//买家已取消
	public static final int ORDERMASTER_STATUS_PAYED = 2;//买家已付款
	public static final int ORDERMASTER_STATUS_DELIVERYED = 3;//卖家已发货
	public static final int ORDERMASTER_STATUS_CONFIRMED = 4;//买家确认收货
	public static final int ORDERMASTER_STATUS_APPRAISED = 5;//买家已评价
	public static final int ORDERMASTER_STATUS_AFTERSALE_COMPLETE = 6;//已完成
	
	
	// Order Goods Detail status
	public static final int AFTERSALE_STATUS_APPLY_REFUND = 1;//买家申请退款
	public static final int AFTERSALE_STATUS_APPLY_RETURN = 2;//买家申请退货
	public static final int AFTERSALE_STATUS_REFUNDING = 3;//买家退货中
	public static final int AFTERSALE_STATUS_CONFIRM_RECV = 4;//卖家确认收货并打款
	public static final int AFTERSALE_STATUS_CONFIRM_REFUND = 5;//卖家已退款,买家确认收款
	public static final int AFTERSALE_STATUS_REJECT_REFUND = 6;//卖家拒绝退款/退货
	public static final int AFTERSALE_STATUS_REFUND_FINISH = 7;//售后完成
	
	

	


}
