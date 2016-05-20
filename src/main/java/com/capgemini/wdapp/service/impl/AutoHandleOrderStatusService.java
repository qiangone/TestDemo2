/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.wdapp.service.IOrderService;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.util.DateUtil;



/**
 * 
 * functional description： seller interface
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "autoHandleOrderStatusService")
public class AutoHandleOrderStatusService  {

	private final static Log logger = LogFactory.getLog(AutoHandleOrderStatusService.class);

	@Autowired
	private IOrderService orderService;
	private String confirmDay ;
	private String appraisalDay ;
	private String finishDay;
	private String asHandleDay;
	private String asConfirmDay ;
	private String asApplyAppealDay ;
	
	public AutoHandleOrderStatusService() {
		 confirmDay = ConfigUtil.getPropertyValue("confirmDay");
		  appraisalDay = ConfigUtil.getPropertyValue("appraisalDay");
		  finishDay = ConfigUtil.getPropertyValue("finishDay");
		  asHandleDay = ConfigUtil.getPropertyValue("asHandleDay");
		  asConfirmDay = ConfigUtil.getPropertyValue("asConfirmDay");
		  asApplyAppealDay = ConfigUtil.getPropertyValue("asApplyAppealDay");
	}
	
	
	
	public void handleAllStatus(){
		logger.info("begein to handle all order status at time:" + DateUtil.dateFormatWithSec(new java.util.Date()));
		  
		  orderService.autoHandleAllOrderStatus(Integer.parseInt(confirmDay), Integer.parseInt(appraisalDay), Integer.parseInt(finishDay), 
				  Integer.parseInt(asHandleDay), Integer.parseInt(asConfirmDay), Integer.parseInt(asApplyAppealDay));
		  logger.info("finished handle all order status at time:" + DateUtil.dateFormatWithSec(new java.util.Date()));
	}
	
	public void handleSingleStatus(Integer masterId){
		
		  logger.info("handleSingleStatus begin");
		  orderService.autoHandleSingleOrderStatus(Integer.parseInt(confirmDay), Integer.parseInt(appraisalDay), Integer.parseInt(finishDay), 
				  Integer.parseInt(asHandleDay), Integer.parseInt(asConfirmDay), Integer.parseInt(asApplyAppealDay), masterId);
		  logger.info("handleSingleStatus end");
	}
	
}
