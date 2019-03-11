package com.bootdo.shop.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.shop.domain.LikbuyDO;
import com.bootdo.shop.service.LikbuyService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author Mickey
 * @email 951449465@qq.com
 * @date 2019-03-11 16:12:28
 */
 
@Controller
@RequestMapping("/shop/likbuy")
public class LikbuyController {
	@Autowired
	private LikbuyService likbuyService;
	
	@GetMapping()
	@RequiresPermissions("shop:likbuy:likbuy")
	String Likbuy(){
	    return "shop/likbuy/likbuy";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("shop:likbuy:likbuy")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<LikbuyDO> likbuyList = likbuyService.list(query);
		int total = likbuyService.count(query);
		PageUtils pageUtils = new PageUtils(likbuyList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("shop:likbuy:add")
	String add(){
	    return "shop/likbuy/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("shop:likbuy:edit")
	String edit(@PathVariable("id") Long id,Model model){
		LikbuyDO likbuy = likbuyService.get(id);
		model.addAttribute("likbuy", likbuy);
	    return "shop/likbuy/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("shop:likbuy:add")
	public R save( LikbuyDO likbuy){
		if(likbuyService.save(likbuy)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("shop:likbuy:edit")
	public R update( LikbuyDO likbuy){
		likbuyService.update(likbuy);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("shop:likbuy:remove")
	public R remove( Long id){
		if(likbuyService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("shop:likbuy:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		likbuyService.batchRemove(ids);
		return R.ok();
	}
	
}
