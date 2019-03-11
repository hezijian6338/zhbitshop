package com.bootdo.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.shop.dao.LikbuyDao;
import com.bootdo.shop.domain.LikbuyDO;
import com.bootdo.shop.service.LikbuyService;



@Service
public class LikbuyServiceImpl implements LikbuyService {
	@Autowired
	private LikbuyDao likbuyDao;
	
	@Override
	public LikbuyDO get(Long id){
		return likbuyDao.get(id);
	}
	
	@Override
	public List<LikbuyDO> list(Map<String, Object> map){
		return likbuyDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return likbuyDao.count(map);
	}
	
	@Override
	public int save(LikbuyDO likbuy){
		return likbuyDao.save(likbuy);
	}
	
	@Override
	public int update(LikbuyDO likbuy){
		return likbuyDao.update(likbuy);
	}
	
	@Override
	public int remove(Long id){
		return likbuyDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return likbuyDao.batchRemove(ids);
	}
	
}
