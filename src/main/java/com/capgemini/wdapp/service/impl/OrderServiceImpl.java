/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.cookie.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.capgemini.wdapp.api.exception.DataAccessDeniedException;
import com.capgemini.wdapp.common.LogisticsJsonObj;
import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.dao.IAddressDao;
import com.capgemini.wdapp.dao.ICartDao;
import com.capgemini.wdapp.dao.IOrderDao;
import com.capgemini.wdapp.dao.IUserDao;
import com.capgemini.wdapp.model.Address;
import com.capgemini.wdapp.model.Cart;
import com.capgemini.wdapp.model.ExpressCompany;
import com.capgemini.wdapp.model.Goods;
import com.capgemini.wdapp.model.OrderAddress;
import com.capgemini.wdapp.model.OrderDetail;
import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;
import com.capgemini.wdapp.model.StockPrice;
import com.capgemini.wdapp.model.User;
import com.capgemini.wdapp.service.IGoodsService;
import com.capgemini.wdapp.service.IOrderService;
import com.capgemini.wdapp.service.IPushService;
import com.capgemini.wdapp.util.CommonUtil;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.util.IdGenerator;
import com.capgemini.wdapp.util.JsonUtil;

/**
 * 
 * functional description： seller interface
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "orderService")
public class OrderServiceImpl implements IOrderService {

	private final static Log logger = LogFactory.getLog(OrderServiceImpl.class);

	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IAddressDao addrDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IPushService pushService;

	@Autowired
	private IGoodsService goodsService;
	
	@Autowired
	private ICartDao cartDao;
	
	private Object obj = new Object();
	
	public void deleteCart(List<Cart> list, Integer userId){
		for(Cart cart : list){
			Map map = new HashMap();
			map.put("userId", userId);
			if(cart.getGoodsId() != null){
				map.put("goodsId", cart.getGoodsId());
			}
			if(cart.getStockPriceId() != null){
				map.put("stockPriceId", cart.getStockPriceId());
			}
			if(cart.getQuantity() != null){
				map.put("quantity", cart.getQuantity());
			}
			
			cartDao.deleteCart(map);
			
		}
	}

	public synchronized void makeOrders(List<Cart> list, Integer userId, Integer shopId,Integer addressId,
			String comments, Float charge) {
		
		List<OrderDetail> detailList = new ArrayList<OrderDetail>();

		float amount = 0;
		for (Cart cart : list) {
			OrderDetail detail = new OrderDetail();
			Integer goodsId = cart.getGoodsId();
			Integer stockPriceId = cart.getStockPriceId();
			Integer quantity = cart.getQuantity();

			detail.setQuantity(quantity);
			detail.setGoodsId(goodsId);
			
			
			Goods goods = goodsService.getGoodsById(goodsId);
			if (goods == null) {
				throw new DataAccessDeniedException("goods id=" + goodsId
						+ " is not exist!");
			}
			detail.setGoodsName(goods.getName());// goods name
			detail.setImages(goods.getImages());
			detail.setDescription(goods.getDescription());
			//detail.setStatus(0);// to do
			
			

			if (!StringUtils.isEmpty(stockPriceId)) {// stock price is not null
				StockPrice sp = goodsService.getStockPriceById(stockPriceId);
				if (sp == null) {
					throw new DataAccessDeniedException("stock price id="
							+ stockPriceId + " is not exist!");
				}

				detail.setStockPriceId(stockPriceId);
				detail.setPrice(sp.getPrice());
				detail.setSpecValueFirst(sp.getSpecValueFirst());

				// update quantity begin
				if(sp.getQuantity()<quantity){
					throw new DataAccessDeniedException("stock is not enough!");
				}
				
				
				sp.setQuantity(sp.getQuantity() - quantity);
				goods.setQuantity(goods.getQuantity() - quantity);
				
				
				goodsService.updateGoods(goods);
				goodsService.updateStockPrice(sp);

				
				// update quantity end

			} else {// get from goods table

				detail.setPrice(goods.getPrice() == null ? 0.00f : goods
						.getPrice());
				// update quantity
				if(goods.getQuantity()<quantity){
					throw new DataAccessDeniedException("stock is not enough!!");
				}
				goods.setQuantity(goods.getQuantity() == null ? 0 : goods
						.getQuantity() - quantity);
				goodsService.updateGoods(goods);
				

			}

			// total amount
			amount += quantity * detail.getPrice();
			detailList.add(detail);
		 }
		
		
		OrderMaster master = new OrderMaster();
		// generate order master code
		String orderCode = IdGenerator.getInstance().getOrderMasterNo(userId);
		master.setOrderCode(orderCode);
		master.setAmount(amount+charge);// add express charge
		master.setUserId(userId);
		master.setComments(comments);
		master.setStatus(0);// to do
		master.setCreateTime(new Date());
		master.setTransportExpense(charge);
		master.setShopId(shopId);

		// add order master
		orderDao.addOrderMaster(master);

		for (OrderDetail de : detailList) {
			de.setOrderMasterId(master.getId());
		}
		

		orderDao.addOrderDetail(detailList);

		// order address
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", addressId);
		List<Address> addrList = addrDao.getAddress(map);
		Address addr = addrList.get(0);
		OrderAddress orderAddr = new OrderAddress();
		orderAddr.setOrderMasterId(master.getId());
		orderAddr.setProvince(addr.getProvince());
		orderAddr.setCity(addr.getCity());
		orderAddr.setDistrict(addr.getDistrict());
		orderAddr.setAddressDetail(addr.getAddressDetail());
		orderAddr.setContactName(addr.getContactName());
		orderAddr.setContactNumber(addr.getContactNumber());
		orderAddr.setZipCode(addr.getZipCode());
		addrDao.addOrderAddress(orderAddr);
		
		//delete from cart
		deleteCart(list, userId);
		
		String tag = ConfigUtil.getPropertyValue("network.tag");
		if(!StringUtils.isEmpty(tag) && tag.equals("1")){
		pushService.newOrder(master);
		}
		
	}

	public OrderMaster getOrderById(Integer masterId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderMasterId", masterId);
		List<OrderMaster> list2 = orderDao.getOrder(map);
		if (list2 != null && list2.size() > 0) {
			OrderMaster om = list2.get(0);
			Map param = new HashMap();
			param.put("id", om.getUserId());
			List<User> userList = userDao.getUser(param);
			if (userList != null && userList.size() > 0) {
				om.setUser(userList.get(0));
			}
			return om;
		}
		return null;
	}
	
	public OrderMaster getOrderByDetailId(Integer detailId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderDetailId", detailId);
		List<OrderMaster> list2 = orderDao.getOrder(map);
		if (list2 != null && list2.size() > 0) {
			OrderMaster om = list2.get(0);
//			Map param = new HashMap();
//			param.put("id", om.getUserId());
//			List<User> userList = userDao.getUser(param);
//			if (userList != null && userList.size() > 0) {
//				om.setUser(userList.get(0));
//			}
			return om;
		}
		return null;
	}
	
	public OrderMaster getOrderByOrderCode(String orderCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderCode", orderCode);
		List<OrderMaster> list2 = orderDao.getOrder(map);
		if (list2 != null && list2.size() > 0) {
			OrderMaster om = list2.get(0);
			Map param = new HashMap();
			param.put("id", om.getUserId());
			List<User> userList = userDao.getUser(param);
			if (userList != null && userList.size() > 0) {
				om.setUser(userList.get(0));
			}
			return om;
		}
		return null;
	}
	
	public void cancelOrderMaster(Integer masterId){
		
		OrderMaster master = new OrderMaster();
		master.setId(masterId);
		master.setStatus(WDConstant.ORDERMASTER_STATUS_CANCELED);
		orderDao.updateOrderMaster(master);
	}
	
	public void confirmRecevie(Integer masterId){
		OrderMaster master = new OrderMaster();
		master.setId(masterId);
		master.setStatus(WDConstant.ORDERMASTER_STATUS_CONFIRMED);
		master.setDealTime(new Date());
		orderDao.updateOrderMaster(master);
	}
	
	public void deliverGoods(Integer masterId, String expressNumber, String expressCorporation){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderMasterId", masterId);
		List<OrderMaster> list = orderDao.getOrder(map);
		if(list != null && list.size()>0){
			OrderMaster master = list.get(0);
			master.setExpressNumber(expressNumber);
			master.setExpressCompany(expressCorporation);
			master.setDeliveryTime(new Date());
			master.setStatus(WDConstant.ORDERMASTER_STATUS_DELIVERYED);
			orderDao.updateOrderMaster(master);
			
			String tag = ConfigUtil.getPropertyValue("network.tag");
			if(!StringUtils.isEmpty(tag) && tag.equals("1")){
			pushService.deliverGoods(master);
			}
		}
	}
	
	

	public PageResults<OrderMaster> getOrderListByPage(
			Map<String, Object> conditions, Pagination page) {
		if (null == page || !page.isValidPage()) {
			return null;
		}

		// get counts
		List<OrderMaster> list = orderDao.getOrder(conditions);
		if (list == null || list.size() == 0) {
			return null;
		}

		int totalCount = list.size();
		page.setTotalCount(totalCount);

		conditions.put("showpage", 1);
		conditions.put(Pagination.STARTINDEX, page.getStartIndex());
		conditions.put(Pagination.PAGESIZE, page.getPageSize());

		List<OrderMaster> list2 = orderDao.getOrder(conditions);
//		if (list2 != null && list2.size() > 0) {
//			for (OrderMaster om : list2) {
//				Map param = new HashMap();
//				param.put("id", om.getUserId());
//				List<User> userList = userDao.getUser(param);
//				if (userList != null && userList.size() > 0) {
//					om.setUser(userList.get(0));
//				}
//			}
//		}

		PageResults<OrderMaster> result = new PageResults<OrderMaster>(page,
				list2);

		return result;
	}
	
	public LogisticsJsonObj getLogisticsInfo(String expressNo, String expressComp){
		ExpressCompany exp = addrDao.getExpressCompByName(expressComp);
		if(exp == null){
			throw new DataAccessDeniedException("not find express company in db");
		}
		String url = exp.getUrl();
		
		String result = CommonUtil.getLogisticsById(expressNo, url);
		if(result == null){
			throw new DataAccessDeniedException("get logistics info error from server");
		}
		String ret = getJsonFormat(result);
		
		LogisticsJsonObj obj = JsonUtil.fromJson(ret, LogisticsJsonObj.class);
		if(obj != null){
			obj.setLogo(exp.getLogo());
		}
		
		return obj;
	}
	
	
	public LogisticsJsonObj getLogisticsInfo(Integer masterId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderMasterId", masterId);
		List<OrderMaster> list2 = orderDao.getOrder(map);
		if (list2 != null && list2.size() > 0) {
			OrderMaster master = list2.get(0);
			String number = master.getExpressNumber();
			String comp = master.getExpressCompany();
			
			ExpressCompany exp = addrDao.getExpressCompByName(comp);
			if(exp == null){
				throw new DataAccessDeniedException("not find express company in db");
			}
			String url = exp.getUrl();
			
			String result = CommonUtil.getLogisticsById(number, url);
			if(result == null){
				throw new DataAccessDeniedException("get logistics info error from server");
			}
			String ret = getJsonFormat(result);
			
			LogisticsJsonObj obj = JsonUtil.fromJson(ret, LogisticsJsonObj.class);
			if(obj != null){
				obj.setLogo(exp.getLogo());
			}
			
			return obj;
		}
		
		return null;
		
	}
	
	private String getJsonFormat(String org){
		try{
			int s = org.indexOf("(");
			int e = org.length()-1;
			String jsonStr = org.substring(s+1, e);
			return jsonStr;
		}catch(Exception e){
			return null;
		}
		
	}
	
	public void orderPay(String orderCode){

		OrderMaster master = new OrderMaster();
		
		master = this.getOrderByOrderCode(orderCode);
		
		if (master.getStatus() != null && master.getStatus() != WDConstant.ORDERMASTER_STATUS_BOOKED) {

			return;
		}
		master.setStatus(WDConstant.ORDERMASTER_STATUS_PAYED);
		orderDao.updateOrderMaster(master);
	}
		
	
	public void orderPay(Integer masterId){
		OrderMaster master = new OrderMaster();
		master = getOrderById(masterId);
		if (master.getStatus() != null &&  master.getStatus()!= WDConstant.ORDERMASTER_STATUS_BOOKED) {
			return;
		}
		master.setStatus(WDConstant.ORDERMASTER_STATUS_PAYED);
		orderDao.updateOrderMaster(master);
	}
	
	
	public int countOrderNumbers(Map<String, Object> map){
		return orderDao.countOrderNumbers(map);
	}
	
	public void autoHandleAllOrderStatus(int confirmDay, int appraisalDay,int finishDay, int asHandleDay, int asConfirmDay, int asApplyAppealDay){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("confirmDay", confirmDay);
		map.put("appraisalDay", appraisalDay);
		map.put("finishDay", finishDay);
		map.put("asHandleDay", asHandleDay);
		map.put("asConfirmDay", asConfirmDay);
		map.put("asApplyAppealDay", asApplyAppealDay);
		
		orderDao.autoHandleAllOrderStatus(map);
	}
	
	public void autoHandleSingleOrderStatus(int confirmDay, int appraisalDay,int finishDay, int asHandleDay, int asConfirmDay, int asApplyAppealDay, int orderMasterId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("confirmDay", confirmDay);
		map.put("appraisalDay", appraisalDay);
		map.put("finishDay", finishDay);
		map.put("asHandleDay", asHandleDay);
		map.put("asConfirmDay", asConfirmDay);
		map.put("asApplyAppealDay", asApplyAppealDay);
		map.put("orderMasterId", orderMasterId);
		
		orderDao.autoHandleSingleOrderStatus(map);
	}
}
