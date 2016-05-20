package com.capgemini.wdapp.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class LogisticsJsonObj {

	private String mailNo;
	private String status;
	private String errCode;
	private String message;
	private String expSpellName;
	private String expTextName;
	private String tel;

	private List<LData> data;

	private String update;
	private String cache;
	private String ord;
	private String logo;
	
	

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	class LData  {
		private String time;
		private String context;

		/**
		 * @return the time
		 */
		public String getTime() {
			return time;
		}

		/**
		 * @param time
		 *            the time to set
		 */
		public void setTime(String time) {
			this.time = time;
		}

		/**
		 * @return the context
		 */
		public String getContext() {
			return context;
		}

		/**
		 * @param context
		 *            the context to set
		 */
		public void setContext(String context) {
			this.context = context;
		}

		

	}

	/**
	 * @return the mailNo
	 */
	public String getMailNo() {
		return mailNo;
	}

	/**
	 * @param mailNo
	 *            the mailNo to set
	 */
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		if (StringUtils.isNotEmpty(status)) {
			if (status.equalsIgnoreCase("3")) {
				status = "已签收";
			} else {
				status = "配送中";
			}
		}
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the errCode
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * @param errCode
	 *            the errCode to set
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the expSpellName
	 */
	public String getExpSpellName() {
		return expSpellName;
	}

	/**
	 * @param expSpellName
	 *            the expSpellName to set
	 */
	public void setExpSpellName(String expSpellName) {
		this.expSpellName = expSpellName;
	}

	/**
	 * @return the expTextName
	 */
	public String getExpTextName() {
		return expTextName;
	}

	/**
	 * @param expTextName
	 *            the expTextName to set
	 */
	public void setExpTextName(String expTextName) {
		this.expTextName = expTextName;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the data
	 */
	public List<LData> getData() {
		
		if(data != null && data.size()>0){
			Collections.sort(data, new Comparator<LData>() {
	            public int compare(LData arg0, LData arg1) {
	            	int flag = arg0.getTime().compareTo(arg1.getTime());
	            	//倒序  
	            	int flag2=0; 
	                if(flag>0){  
	                    flag2=-1;  
	                }else if(flag<0){  
	                    flag2=1;  
	                }  
	                return flag2; 
	            }
	        });
		}
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<LData> data) {
		
		this.data = data;
	}

	/**
	 * @return the update
	 */
	public String getUpdate() {
		return update;
	}

	/**
	 * @param update
	 *            the update to set
	 */
	public void setUpdate(String update) {
		this.update = update;
	}

	/**
	 * @return the cache
	 */
	public String getCache() {
		return cache;
	}

	/**
	 * @param cache
	 *            the cache to set
	 */
	public void setCache(String cache) {
		this.cache = cache;
	}

	/**
	 * @return the ord
	 */
	public String getOrd() {
		return ord;
	}

	/**
	 * @param ord
	 *            the ord to set
	 */
	public void setOrd(String ord) {
		this.ord = ord;
	}

}
