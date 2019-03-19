package com.bootdo.wap;


import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.shop.domain.*;
import com.bootdo.shop.service.*;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.utils.ChangePageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zsCat 2016-10-31 14:01:30
 * @version 4.0v
 * 商品管理
 * @Email: 951449465@qq.com
 */
@Controller
@RequestMapping("/taobao/person")
public class TaoBaoPersonController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private TArticleService tArticleService;
    @Autowired
    private TBrandService tBrandService;
    @Autowired
    private TGoodsTypeService tGoodsTypeService;
    @Autowired
    private TGoodsService tGoodsService;
    @Autowired
    private TGoodsClassService tGoodsClassService;
    @Autowired
    private TMemberService tMemberService;
    @Autowired
    private TCartService tCartService;
    @Autowired
    private TOrderService orderService;
    @Autowired
    private TGoodSorderService tGoodSorderService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private LikbuyService likbuyService;
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public ModelAndView index() {
        try {
            ModelAndView model = new ModelAndView("/taobao/self");
            TMemberDO member = MemberUtils.getSessionLoginUser();
//				如果member为空 转入登录页面
            if (member == null) {
                ModelAndView model1 = new ModelAndView("taobao/login");
                return model1;
            } else {
                model.addObject("member", member);
                return model;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导航失败!");
        }
    }

    //个人资料
    @RequestMapping("/profile")
    public ModelAndView profile() {
        ModelAndView model = new ModelAndView("/taobao/profile");
        TMemberDO member = MemberUtils.getSessionLoginUser();
        model.addObject("member", member);
        return model;
    }


    @RequestMapping("/change_name")
    public ModelAndView change_name() {
        ModelAndView model = new ModelAndView("/taobao/change_name");
        TMemberDO member = MemberUtils.getSessionLoginUser();
        model.addObject("member", member);
        return model;
    }

    @RequestMapping("/change_tel")
    public ModelAndView change_tel() {
        ModelAndView model = new ModelAndView("/taobao/change_tel");
        TMemberDO member = MemberUtils.getSessionLoginUser();
        model.addObject("member", member);
        return model;
    }

    @RequestMapping("/change_pwd")
    public ModelAndView change_pwd() {
        ModelAndView model = new ModelAndView("/taobao/change_tel");

        return model;
    }

    @RequestMapping("/favorite")
    public ModelAndView favorite() {
        ModelAndView model = new ModelAndView("/taobao/mycollect");
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userid", MemberUtils.getSessionLoginUser().getId());
            Query query = new Query(params);
            List<FavoriteDO> favoriteList = favoriteService.userFavorite(query);
            model.addObject("favoriteList", favoriteList);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return model;
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "updateUser1", method = RequestMethod.POST)
    public ModelAndView updateUser1(@ModelAttribute TMemberDO Member, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/taobao/profile");
        tMemberService.update(Member);
        return model;
    }

    /**
     * 保存用户
     *
     * @return
     */
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute TMemberDO Member, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/taobao/profile");
        tMemberService.update(Member);
        return model;
    }

    // 自定义比较器：按销售情况排序
    static class SellHitComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            TGoodsDO p1 = (TGoodsDO) object1; // 强制转换
            TGoodsDO p2 = (TGoodsDO) object2;
            return p2.getSellhit().compareTo(p1.getSellhit());
        }
    }

    // 自定义比较器：按书出版时间来排序
    static class CalendarComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            TGoodsDO p1 = (TGoodsDO) object1; // 强制转换
            TGoodsDO p2 = (TGoodsDO) object2;
            return p2.getCreateDate().compareTo(p1.getCreateDate());
        }
    }

    /**
     * 个人信息
     *
     * @return
     */
    @RequestMapping("/information")
    public ModelAndView information() {
        ModelAndView model = new ModelAndView("/taobao/datum");
        TMemberDO member = MemberUtils.getSessionLoginUser();
        model.addObject("member", member);
        return model;
    }

    /**
     * 安全管理
     *
     * @return
     */
    @RequestMapping("/safety")
    public ModelAndView safety() {
        ModelAndView model = new ModelAndView("/taobao/safety");


        return model;
    }
    /**
     * 根据父类ID 获取到 下级城市
     *
     * @param @param  parentid
     * @param @return
     * @param @throws JsonGenerationException
     * @param @throws JsonMappingException
     * @param @throws Exception    设定文件
     * @return Map<String,String>    返回类型
     * @throws
     * @Title: getChildArea
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */


    /**
     * 订单管理
     * 需要根据不同的status 跳转到不同的status页
     *
     * @return
     */
    @RequestMapping("/order")
    public ModelAndView order(HttpServletRequest request) {
        String status = request.getParameter("status");
//		 System.out.print(status);
        String orderURL = "/taobao/order";
        if (status.equals("1")) {
            orderURL = orderURL + "four";
        } else if (status.equals("2")) {
            orderURL = orderURL + "two";
        } else if (status.equals("9")) {
            orderURL = orderURL + "three";
        } else if (status.equals("3")) {
            orderURL = orderURL + "one";
        }
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNoneEmpty(status) && (!status.equals("4"))) {
            params.put("status", status);
            System.out.println(status);
        }
        params.put("userid", MemberUtils.getSessionLoginUser().getId());
        Query query = new Query(params);
        List<TOrderDO> tOrderList = orderService.list(query);
        int i = 0;
        for (TOrderDO orderDO : tOrderList) {
            System.out.println(tOrderList.get(i).getId());
            params.clear();
            params.put("orderid", orderDO.getId());
            List<TGoodSorderDO> gs = tGoodSorderService.list(params);
            orderDO.setGoodsList(gs);
            ++i;
        }
        System.out.println(orderURL);
        ModelAndView model = new ModelAndView(orderURL);
        model.addObject("tOrderList", tOrderList);
        return model;
    }

    /**
     * 物流(后续开发)
     *
     * @return
     */
    @RequestMapping("/wuliu/id")
    public ModelAndView wuliu(@PathVariable("id") Long id) {
        ModelAndView model = new ModelAndView("/taobao/wuliu");
        System.out.println(id);
        return model;
    }

    @RequestMapping("/ajax/order")
    public String orderList(HttpServletRequest request) {
        try {
            String id = request.getParameter("order");
            if (id != null && !id.equals("")) {
                Map<String, Object> params = new HashMap<>();
                params.put("userid", MemberUtils.getSessionLoginUser().getId());
                params.put("status", Integer.parseInt(id));
                List<TOrderDO> orderList = orderService.list(params);
                //	request.setAttribute("imgServer", "http://image.zscat.com");
                request.setAttribute("orderList", orderList);
            }
        } catch (Exception e) {

        }
        return "taobao/ajax-order";
    }

    /**
     * 退款管理
     *
     * @return
     */
    @RequestMapping("/change")
    public ModelAndView change() {
        ModelAndView model = new ModelAndView("/taobao/change");


        return model;
    }

    /**
     * 我的积分
     *
     * @return
     */
    @RequestMapping("/points")
    public ModelAndView points() {
        ModelAndView model = new ModelAndView("/taobao/points");


        return model;
    }

    /**
     * 优惠劵
     *
     * @return
     */
    @RequestMapping("/coupon")
    public ModelAndView coupon() {
        ModelAndView model = new ModelAndView("/taobao/myCoupon");
        Map<String, Object> params = new HashMap<>();
        params.put("userid", MemberUtils.getSessionLoginUser().getId());
        Query query = new Query(params);
        List<CouponDO> couponList = couponService.userCoupon(query);
        model.addObject("couponList", couponList);
        return model;
    }

    /**
     * 红包
     *
     * @return
     */
    @RequestMapping("/bonus")
    public ModelAndView bonus() {
        ModelAndView model = new ModelAndView("/taobao/bonus");


        return model;
    }

    /**
     * 账户余额
     *
     * @return
     */
    @RequestMapping("/walletlist")
    public ModelAndView walletlist() {
        TMemberDO member = MemberUtils.getSessionLoginUser();

        //如果member为空 转入登录页面
        if (member == null) {
            ModelAndView model1 = new ModelAndView("taobao/login");
            return model1;
        }
        ModelAndView model = new ModelAndView("/taobao/walletlist");
        return model;
    }

    /**
     * 账单明细
     *
     * @return
     */
    @RequestMapping("/bill")
    public ModelAndView bill() {
        ModelAndView model = new ModelAndView("/taobao/bill");


        return model;
    }

    /**
     * 足迹
     *
     * @return
     */
    @RequestMapping("/foot")
    public ModelAndView foot(HttpServletRequest req) {
        ModelAndView model = new ModelAndView("/taobao/foot");

//		    RedisUtils  RedisUtils=new RedisUtils();
//			Map<String,String> map=RedisUtils.hgetall(MemberUtils.SHOPPING_HISTORY+ip);
//			List<Object> TGoodsList=JsonUtils.readJsonList(JsonUtils.toJsonStr(map), TGoods.class);
//			model.addObject("TGoodsList",TGoodsList);
        return model;
    }

    /**
     * 商品咨询
     *
     * @return
     */
    @RequestMapping("/consultation")
    public ModelAndView consultation() {
        ModelAndView model = new ModelAndView("/taobao/consultation");


        return model;
    }

    /**
     * 页面分类显示
     *
     * @return
     */
    @RequestMapping("/typelist")
    public ModelAndView goodsType(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/taobao/index-bak");
        //从t_goods_class 获取出相关的数据
        Map<String, Object> map = new HashMap<>();
        map.put("del_flag",1);
        List<TGoodsTypeDO> list = tGoodsTypeService.list(map);
        request.setAttribute("typeList", list);
        return model;
    }

    /**
     * 意见反馈
     *
     * @return
     */
    @RequestMapping("/suggest")
    public ModelAndView suggest() {
        TMemberDO member = MemberUtils.getSessionLoginUser();

        //如果member为空 转入登录页面
        if (member == null) {
            ModelAndView model1 = new ModelAndView("taobao/login");
            return model1;
        }
        ModelAndView model = new ModelAndView("/taobao/suggest");

        return model;
    }

    /**
     * 我的消息
     *
     * @return
     */
    @RequestMapping("/msg")
    public ModelAndView news() {
        TMemberDO member = MemberUtils.getSessionLoginUser();

        //如果member为空 转入登录页面
        if (member == null) {
            ModelAndView model1 = new ModelAndView("taobao/login");
            return model1;
        }
        ModelAndView model = new ModelAndView("/taobao/news");
        return model;
    }

    /**
     * 购物车
     *
     * @return
     */
    @RequestMapping("/cartList")
    public ModelAndView cartList() {
        ModelAndView model = new ModelAndView("/taobao/shopcar");
        if (MemberUtils.getSessionLoginUser() != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("userid", MemberUtils.getSessionLoginUser().getId());
            model.addObject("cartList", tCartService.list(params));
        } else {
            model.addObject("cartList", new ArrayList<>());
        }
        return model;
    }

    /**
     * 立即购买
     * 跳转到购买页面
     * 需要接受一堆的数据 接受搜要购买的物品的用户详细信息
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/LikBuy/{TGoodsId}")
    public ModelAndView TGoodsDetail(@PathVariable("TGoodsId") Long TGoodsId,
                                     HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        TMemberDO member = MemberUtils.getSessionLoginUser();

        //如果member为空 转入登录页面
        if (member == null) {
            ModelAndView model1 = new ModelAndView("taobao/login");
            return model1;
        }
        //登录后开始跳转到相关的支付页面
        Map<String, Object> params = new HashMap<>();
        Long userid = MemberUtils.getSessionLoginUser().getId();
        params.put("userid", userid);

        //传用户的相关数据(还有地址还没有成功传递)
        TMemberDO userDO = tMemberService.get(userid);
        mav.addObject("user",userDO);

        //根据用户确定地址列表
        Map<String, Object> params1 = new HashMap<>();
        params1.put("userid", userid);
        List<AddressDO> addressList = addressService.list(params1);
        mav.addObject("addressList", addressList);
        mav.addObject("address", addressList.get(0));

        //传递所要购买的物品的数据 根据上面传过来的 TGoodsId 进行传送
        TGoodsDO tGoodsDO = tGoodsService.get(TGoodsId);
        mav.addObject("goods",tGoodsDO);

//      List<LikbuyDO> likbuyDOList = likbuyService.list(params);
//      mav.addObject("likbuyDoList", likbuyDOList);
//      mav.addObject("goods", tGoodsService.get(TGoodsId));
        mav.setViewName("taobao/buy");
        return mav;
    }

    /**
     * 提交订单
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("submitOrder")
    public ModelAndView submitOrder(@RequestParam(value = "productId") Long productId,
                                    @RequestParam(value = "addressid") Long addressid,
                                    @RequestParam(value = "paymentid") Long paymentid,
                                    @RequestParam(value = "paymentid", defaultValue = "无留言") String usercontent
    ) throws Exception {
        ModelAndView mav = new ModelAndView();
        TMemberDO m = MemberUtils.getSessionLoginUser();
        TOrderDO order = orderService.insertWapOrder(productId, addressid, paymentid, usercontent, m.getId(), m.getUsername());

        if (order == null) {
            mav.setViewName("taobao/forwad");
        } else {
            mav.setViewName("taobao/success");
        }

        mav.addObject("order", order);
        return mav;
    }


    /**
     * 查看已买到的宝贝
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/selledTGoods/{id}")
    public ModelAndView selledTGoods(@PathVariable("id") Long id) throws Exception {
        ModelAndView mav = new ModelAndView();
        TGoodsDO b = tGoodsService.get(id);
        mav.addObject("TGoods", b);
        mav.setViewName("taobao/order");
        return mav;
    }

    /**
     * 交易详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/orderDetail/{id}")
    public ModelAndView orderDetail(@PathVariable("id") Long id) throws Exception {
        ModelAndView mav = new ModelAndView();
        TGoodsDO b = tGoodsService.get(id);
        mav.addObject("TGoods", b);
        mav.setViewName("taobao/orderinfo");
        return mav;
    }

    /**
     * 物流
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/logistics/{id}")
    public ModelAndView logistics(@PathVariable("id") Long id) throws Exception {
        ModelAndView mav = new ModelAndView();
        TGoodsDO b = tGoodsService.get(id);
        mav.addObject("TGoods", b);
        mav.setViewName("taobao/orderinfo");
        return mav;
    }

    /**
     * 加入购物车
     *
     * @param goodsid
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addCart", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> addCart(@RequestParam(value = "goodsid") Long goodsid) throws Exception {
        Map<String, String> map = new HashMap<>();

        TGoodsDO goods = tGoodsService.get(goodsid);
        TCartDO cart = new TCartDO();
        cart.setGoodsid(goodsid);
        Long userid = MemberUtils.getSessionLoginUser().getId();
        cart.setUserid(userid);
        Map<String, Object> params = new HashMap<>();
        params.put("userid", userid);
        params.put("goodsid", goodsid);
        TCartDO check = tCartService.selectOne(params);
        int result = 0;
        if (check == null) {
            cart.setCount(1);
            cart.setGoodsname(goods.getTitle());
            cart.setImg(goods.getImg());
            cart.setStoreid(goods.getStoreid());
            cart.setPrice(goods.getPrices());
            result = tCartService.save(cart);
        } else {
            check.setCount(check.getCount() + 1);
            result = tCartService.update(check);
        }

        if (result == 1) {
            map.put("success", "true");
        } else {
            map.put("success", "false");
        }
        return map;
    }


    /**
     * 删除购物车
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> deleteCart(@RequestParam(value = "id") Long id) throws Exception {
        tCartService.remove(id);
        Map<String, String> map = new HashMap<>();
        map.put("success", "true");
        return map;
    }

    /**
     * 删除订单，修改状态为9
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> deleteOrder(@RequestParam(value = "id") Long id) throws Exception {
        TOrderDO o = new TOrderDO();
        o.setStatus(9);
        o.setId(id);
        orderService.update(o);
        Map<String, String> map = new HashMap<>();
        map.put("success", "true");
        return map;
    }

    /**
     * 重置密码
     * 转入页面为重置密码页面 第一步 首先输入正确的旧密码
     */
//	@RequestMapping("/resetPW")
//	public ModelAndView changePassword() {
//		ModelAndView model = new ModelAndView("/taobao/resetPW");
//		return model;
//	}

    /**
     * 重置密码
     * 转入页面为重置密码页面 第二步 输入新的密码  （一步过）
     */
    @RequestMapping("/resetPassword")
    public ModelAndView changePassword2() {
        ModelAndView model = new ModelAndView("/taobao/resetPW2");
        return model;
    }

    /**
     * 跳转地址页面
     *
     * @return
     */
    @RequestMapping("/address")
    public ModelAndView checkAddress() {
        ModelAndView model = new ModelAndView("/taobao/addres");
        Map<String, Object> addressobj = new HashMap<>();
//		System.out.println(MemberUtils.getSessionLoginUser().getId());
        addressobj.put("userid", MemberUtils.getSessionLoginUser().getId());
        List<AddressDO> addresslist = addressService.list(addressobj);
        model.addObject("addressList", addresslist);
        return model;
    }

    /**
     * 跳转添加地址页面
     */
    @RequestMapping("/go-address")
    public ModelAndView addAddress() {
        ModelAndView model = new ModelAndView("/taobao/go-address");
        model.addObject("username", MemberUtils.getSessionLoginUser().getUsername());
        System.out.println(MemberUtils.getSessionLoginUser().getUsername());
        return model;
    }


    @RequestMapping(value = "/save-address", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveAddress(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "userphone", required = true) String userphone,
            @RequestParam(value = "provinceUser", required = true) String provinceUser,
            @RequestParam(value = "cityUser", required = true) String cityUser,
            @RequestParam(value = "distUser", required = true) String distUser,
            @RequestParam(value = "detailArea", required = true) String detailArea, HttpServletRequest request
    ) {

        String detail = provinceUser + cityUser + distUser + detailArea;
        AddressDO addressDO = new AddressDO();
        addressDO.setName(username);
        addressDO.setUserid(MemberUtils.getSessionLoginUser().getId());
        addressDO.setMobile(userphone);
        addressDO.setDetail(detail);
        addressDO.setIsdefault(1);
        ModelAndView model = new ModelAndView("/taobao/person");

        try {
            HttpSession session = request.getSession();
            int result = addressService.save(addressDO);
            if (result != 1) {
                return model;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return model;
        }
        return model;
    }


    @RequestMapping("/information/{typeid}")
    public ModelAndView typeInformation(@PathVariable("typeid") int typeid) {
        ModelAndView model = new ModelAndView("/taobao/typeGoodsList");
//      TMemberDO member = tMemberService.get(createBy);
        Map<String, Object> map = new HashMap<>();
        map.put("typeid",typeid);
        List<TGoodsDO> goodslist = tGoodsService.list(map);
        for(int i = 0 ; i < goodslist.size() ; i++) {
            System.out.println(goodslist.get(i).getTitle());
        }
        model.addObject("goodslist", goodslist);
//        model.addObject("member", member);
        return model;
    }
}
