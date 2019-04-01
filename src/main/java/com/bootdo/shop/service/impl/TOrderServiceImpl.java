package com.bootdo.shop.service.impl;

import com.bootdo.common.utils.RandomString;
import com.bootdo.shop.dao.TGoodSorderDao;
import com.bootdo.shop.dao.TGoodsDao;
import com.bootdo.shop.dao.TOrderLogDao;
import com.bootdo.shop.domain.TGoodSorderDO;
import com.bootdo.shop.domain.TGoodsDO;
import com.bootdo.shop.domain.TOrderLogDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bootdo.shop.dao.TOrderDao;
import com.bootdo.shop.domain.TOrderDO;
import com.bootdo.shop.service.TOrderService;



@Service
public class TOrderServiceImpl implements TOrderService {
	@Autowired
	private TOrderDao tOrderDao;
	@Autowired
	private TOrderLogDao tOrderLogDao;
	@Autowired
	private TGoodsDao tGoodsDao;
	@Autowired
	private TGoodSorderDao tGoodSorderDao;
	
	@Override
	public TOrderDO get(Long id){
		return tOrderDao.get(id);
	}
	
	@Override
	public List<TOrderDO> list(Map<String, Object> map){
		return tOrderDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return tOrderDao.count(map);
	}
	
	@Override
	public int save(TOrderDO tOrder){
		return tOrderDao.save(tOrder);
	}
	
	@Override
	public int update(TOrderDO tOrder){
		return tOrderDao.update(tOrder);
	}
	
	@Override
	public int remove(Long id){
		return tOrderDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return tOrderDao.batchRemove(ids);
	}

	@Override
	public TOrderDO insertWapOrder(Long productId, Long addressid, Long paymentid, String usercontent, Long id, String username,int totalCount) {
		TOrderDO order=new TOrderDO();
		TGoodsDO p =tGoodsDao.get(productId);
		order.setOrdersn(RandomString.generateRandomString(8));
		order.setCreatedate(new Date());
		order.setStatus(9);
		order.setUserid(id);
		order.setUsername(username);
		order.setPaymentid(paymentid);
		order.setUsercontent(usercontent);
		order.setAddressid(addressid);
		order.setTotalcount(totalCount);
		order.setTotalprice(BigDecimal.valueOf(Double.valueOf(p.getPrices())));
		tOrderDao.save(order);

		TGoodSorderDO tGoodSorderDO = new TGoodSorderDO();
		tGoodSorderDO.setCount(totalCount);
		tGoodSorderDO.setGoodsid(productId);
		tGoodSorderDO.setOrderid(order.getId());
		tGoodSorderDO.setImg(p.getImg());
		tGoodSorderDO.setGoodsname(p.getTitle());
		tGoodSorderDO.setPrice(String.valueOf(totalCount * Integer.parseInt(p.getPrices())));
		tGoodSorderDao.save(tGoodSorderDO);

		TOrderLogDO log=new TOrderLogDO();
		log.setOrderId(order.getId());
		log.setOrderState("1");
		log.setStateInfo("提交订单");
		log.setCreateTime(new Date().getTime());
		log.setOperator(username);
		tOrderLogDao.save(log);

		return order;
	}

}
