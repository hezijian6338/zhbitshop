package com.bootdo.shop.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author Mickey
 * @email 951449465@qq.com
 * @date 2019-03-11 16:12:28
 */
public class LikbuyDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Long goodsid;
	//
	private Long userid;
	//商品
	private String goodsname;
	//价格
	private String price;
	//个数
	private Integer count;
	//图片
	private String img;
	//
	private Long storeid;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}
	/**
	 * 获取：
	 */
	public Long getGoodsid() {
		return goodsid;
	}
	/**
	 * 设置：
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	/**
	 * 获取：
	 */
	public Long getUserid() {
		return userid;
	}
	/**
	 * 设置：商品
	 */
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	/**
	 * 获取：商品
	 */
	public String getGoodsname() {
		return goodsname;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * 设置：个数
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取：个数
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置：图片
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：图片
	 */
	public String getImg() {
		return img;
	}
	/**
	 * 设置：
	 */
	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}
	/**
	 * 获取：
	 */
	public Long getStoreid() {
		return storeid;
	}
}
