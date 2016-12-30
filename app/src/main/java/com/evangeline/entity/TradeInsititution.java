
/**   
 * @Title: TradeInsititution.java 
 * @Package: com.app.model 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年10月13日 下午2:32:22 
 * @version 1.3.1 
 */


package com.evangeline.entity;

/** 
 * @Description 支付方式
 * @author Selena Wong
 * @date 2016年10月13日 下午2:32:22 
 * @version V1.3.1
 */

public class TradeInsititution {
	
	private String name;//商家名称
	private int logoId;//竖向logo
	private int landscapeLogoId;//横向标题logo
	private int level;//show level
	private int payId;//支付id
	
	public TradeInsititution(String name,int logoId,int landscapeLogoId,int level,int payId){
		this.name = name;
		this.logoId = logoId;
		this.landscapeLogoId = landscapeLogoId;
		this.level = level;
		this.payId = payId;
	}

	
	/**
	 * @return name
	 */
	
	public String getName() {
		return name;
	}

	
	/** 
	 * @param name 要设置的 name 
	 */
	
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * @return logoId
	 */
	
	public int getLogoId() {
		return logoId;
	}

	
	/** 
	 * @param logoId 要设置的 logoId 
	 */
	
	public void setLogoId(int logoId) {
		this.logoId = logoId;
	}

	/**
	 * @return landscapeLogoId
	 */
	
	public int getLandscapeLogoId() {
		return landscapeLogoId;
	}


	
	/** 
	 * @param landscapeLogoId 要设置的 landscapeLogoId 
	 */
	
	public void setLandscapeLogoId(int landscapeLogoId) {
		this.landscapeLogoId = landscapeLogoId;
	}


	/**
	 * @return level
	 */
	
	public int getLevel() {
		return level;
	}


	
	/** 
	 * @param level 要设置的 level 
	 */
	
	public void setLevel(int level) {
		this.level = level;
	}


	
	/**
	 * @return payId
	 */
	
	public int getPayId() {
		return payId;
	}


	
	/** 
	 * @param payId 要设置的 payId 
	 */
	
	public void setPayId(int payId) {
		this.payId = payId;
	}
	
	
}
