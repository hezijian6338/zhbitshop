package com.bootdo.shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.system.controller.SessionController;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.wap.MemberUtils;
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

import com.bootdo.shop.domain.TOrderDO;
import com.bootdo.shop.service.TOrderService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zscat
 * @email 951449465@qq.com
 * @date 2017-10-15 15:07:37
 */

@Controller
@RequestMapping("/shop/tOrder")
public class TOrderController {
    @Autowired
    private TOrderService tOrderService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("shop:tOrder:tOrder")
    String TOrder() {

        HttpSession session = getCurRequest().getSession();
        System.out.println();
        Map<String, Object> map = new HashMap<>();
        map.put("username", session.getAttribute("username"));
        UserDO userDO = userService.list(map).get(0);
        if (userDO.getroleIds().get(0) == 56) {
            return "shop/tOrder/tOrder";
        } else {
            return "shop/tOrder/tOrder";
        }
    }

    public static HttpServletRequest getCurRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null && requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("shop:tOrder:tOrder")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<TOrderDO> tOrderList = tOrderService.list(query);
        int total = tOrderService.count(query);
        PageUtils pageUtils = new PageUtils(tOrderList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("shop:tOrder:add")
    String add() {
        return "shop/tOrder/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("shop:tOrder:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        TOrderDO tOrder = tOrderService.get(id);
        model.addAttribute("tOrder", tOrder);
        return "shop/tOrder/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("shop:tOrder:add")
    public R save(TOrderDO tOrder) {
        if (tOrderService.save(tOrder) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("shop:tOrder:edit")
    public R update(TOrderDO tOrder) {
        tOrderService.update(tOrder);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("shop:tOrder:remove")
    public R remove(Long id) {
        if (tOrderService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("shop:tOrder:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        tOrderService.batchRemove(ids);
        return R.ok();
    }

}
