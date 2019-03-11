package com.bootdo.shop.dao;

import com.bootdo.shop.domain.LikbuyDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author Mickey
 * @email 951449465@qq.com
 * @date 2019-03-11 16:12:28
 */
@Mapper
public interface LikbuyDao {

	LikbuyDO get(Long id);
	
	List<LikbuyDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LikbuyDO likbuy);
	
	int update(LikbuyDO likbuy);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
