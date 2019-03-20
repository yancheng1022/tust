package com.tust.order.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.PageInfo;
import com.tust.VO.CartVo;
import com.tust.mapper.ItemMapper;
import com.tust.mapper.OrderItemMapper;
import com.tust.mapper.OrderMapper;
import com.tust.mapper.PayLogMapper;
import com.tust.order.config.AlipayConfig;
import com.tust.order.service.OrderService;
import com.tust.pojo.*;
import com.tust.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/29 20:18
 * @Description: 服务层实现
*/
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private PayLogMapper payLogMapper;

	@Override
	public List<Order> findAll() {
		return orderMapper.selectByExample(null);
	}


	@Override
	public PageInfo findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Order> orders = orderMapper.selectByExample(null);
		return new PageInfo(orders);
	}

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private IdWorker idWorker;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private ItemMapper itemMapper;
	
	/**
	 * 增加
	 */
	@Override
	public void add(Order order) {
		
		//1.从redis中提取购物车列表
		List<CartVo> cartList= (List<CartVo>) redisTemplate.boundHashOps("cartList").get(order.getUserId());
		int length = cartList.size();
		List<String> orderIds=new ArrayList();//订单ID集合
		int i=0;
		double total_money=0;//总金额
		//2.循环购物车列表添加订单
		for(CartVo  cart:cartList){
			Order tbOrder=new Order();
			long orderId = idWorker.nextId();	//获取ID		
			tbOrder.setOrderId(orderId);
			tbOrder.setPaymentType(order.getPaymentType());//支付类型
			tbOrder.setStatus("1");//未付款 
			tbOrder.setCreateTime(new Date());//下单时间
			tbOrder.setUpdateTime(new Date());//更新时间
			tbOrder.setUserId(order.getUserId());//当前用户
			tbOrder.setReceiverAreaName(order.getReceiverAreaName());//收货人地址
			tbOrder.setReceiverMobile(order.getReceiverMobile());//收货人电话
			tbOrder.setReceiver(order.getReceiver());//收货人
			tbOrder.setSourceType(order.getSourceType());//订单来源
			tbOrder.setSellerId(order.getSellerId());//商家ID

			double money=0;//合计数
			//循环购物车中每条明细记录
			for(OrderItem orderItem:cart.getOrderItemList()  ){
				orderItem.setId(idWorker.nextId());//主键
				orderItem.setOrderId(orderId);//订单编号
				orderItem.setSellerId(cart.getSellerId());//商家ID
				orderItemMapper.insert(orderItem);				
				money+=orderItem.getTotalFee().doubleValue();
			}
			
			tbOrder.setPayment(new BigDecimal(money));//合计
			orderMapper.insert(tbOrder);
			orderIds.add(orderId+"");
			total_money+=money;

		}
		//添加支付日志
		if ("1".equals(order.getPaymentType())){
			PayLog payLog = new PayLog();
			payLog.setOutTradeNo(idWorker.nextId()+"");
			payLog.setCreateTime(new Date());
			payLog.setUserId(order.getUserId());
			payLog.setOrderList(orderIds.toString().replace("[", "").replace("]", ""));
			payLog.setTotalFee((long)total_money);
			payLog.setTradeState("0");

			payLogMapper.insert(payLog);

			//放入缓存
			redisTemplate.boundHashOps("payLog").put(order.getUserId(), payLog);
		}
		//3.清除redis中的购物车
		redisTemplate.boundHashOps("cartList").delete(order.getUserId());

	}

	

	@Override
	public void update(Order order){
		orderMapper.updateByPrimaryKey(order);
	}	
	

	@Override
	public Order findOne(Long id){
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			orderMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageInfo findPage(Order order, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		OrderExample example=new OrderExample();
		OrderExample.Criteria criteria = example.createCriteria();
		
		if(order!=null){			
						if(order.getPaymentType()!=null && order.getPaymentType().length()>0){
				criteria.andPaymentTypeLike("%"+order.getPaymentType()+"%");
			}
			if(order.getPostFee()!=null && order.getPostFee().length()>0){
				criteria.andPostFeeLike("%"+order.getPostFee()+"%");
			}
			if(order.getStatus()!=null && order.getStatus().length()>0){
				criteria.andStatusLike("%"+order.getStatus()+"%");
			}
			if(order.getShippingName()!=null && order.getShippingName().length()>0){
				criteria.andShippingNameLike("%"+order.getShippingName()+"%");
			}
			if(order.getShippingCode()!=null && order.getShippingCode().length()>0){
				criteria.andShippingCodeLike("%"+order.getShippingCode()+"%");
			}
			if(order.getUserId()!=null && order.getUserId().length()>0){
				criteria.andUserIdLike("%"+order.getUserId()+"%");
			}
			if(order.getBuyerMessage()!=null && order.getBuyerMessage().length()>0){
				criteria.andBuyerMessageLike("%"+order.getBuyerMessage()+"%");
			}
			if(order.getBuyerNick()!=null && order.getBuyerNick().length()>0){
				criteria.andBuyerNickLike("%"+order.getBuyerNick()+"%");
			}
			if(order.getBuyerRate()!=null && order.getBuyerRate().length()>0){
				criteria.andBuyerRateLike("%"+order.getBuyerRate()+"%");
			}
			if(order.getReceiverAreaName()!=null && order.getReceiverAreaName().length()>0){
				criteria.andReceiverAreaNameLike("%"+order.getReceiverAreaName()+"%");
			}
			if(order.getReceiverMobile()!=null && order.getReceiverMobile().length()>0){
				criteria.andReceiverMobileLike("%"+order.getReceiverMobile()+"%");
			}
			if(order.getReceiverZipCode()!=null && order.getReceiverZipCode().length()>0){
				criteria.andReceiverZipCodeLike("%"+order.getReceiverZipCode()+"%");
			}
			if(order.getReceiver()!=null && order.getReceiver().length()>0){
				criteria.andReceiverLike("%"+order.getReceiver()+"%");
			}
			if(order.getInvoiceType()!=null && order.getInvoiceType().length()>0){
				criteria.andInvoiceTypeLike("%"+order.getInvoiceType()+"%");
			}
			if(order.getSourceType()!=null && order.getSourceType().length()>0){
				criteria.andSourceTypeLike("%"+order.getSourceType()+"%");
			}
			if(order.getSellerId()!=null && order.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+order.getSellerId()+"%");
			}
	
		}

			List<Order> orders = orderMapper.selectByExample(example);
			return new PageInfo(orders);
	}

	@Override
	public String goAliPay(String username) throws AlipayApiException {

		PayLog payLog = (PayLog)redisTemplate.boundHashOps("payLog").get(username);
		BigDecimal total = new BigDecimal(payLog.getTotalFee());


		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
//		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = payLog.getOutTradeNo();
		//付款金额，必填
		String total_amount = total.toString();
		//订单名称，必填
		String subject = "TUST";
		//商品描述，可空
//		String body = "用户订购商品个数：" + order.getBuyCounts();

		// 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
		String timeout_express = "1c";

		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
				+ "\"total_amount\":\""+ total_amount +"\","
				+ "\"subject\":\""+ subject +"\","
				+ "\"timeout_express\":\""+ timeout_express +"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		//请求
		String result = alipayClient.pageExecute(alipayRequest).getBody();

		return result;
	}

	@Override
	public void paySuccess(String out_trade_no,String trade_no) {
		//修改支付日志的状态和相关字段
		PayLog payLog = payLogMapper.selectByPrimaryKey(out_trade_no);
		payLog.setPayTime(new Date());
		payLog.setTradeState("1");
		payLog.setTransactionId(trade_no);
		payLogMapper.updateByPrimaryKey(payLog);

		//修改订单状态 商品状态
		String orderList = payLog.getOrderList();
		String[] orderIds = orderList.split(",");
		for (String orderId:orderIds){
			Order order = orderMapper.selectByPrimaryKey(Long.valueOf(orderId));
			order.setStatus("2");//已付款状态
			order.setPaymentTime(new Date());//支付时间
			orderMapper.updateByPrimaryKey(order);

			//修改商品状态
			OrderItemExample orderItemExample = new OrderItemExample();
			OrderItemExample.Criteria criteria = orderItemExample.createCriteria();
			criteria.andOrderIdEqualTo(Long.valueOf(orderId));
			List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
			for (OrderItem orderItem:orderItems){
				ItemExample itemExample = new ItemExample();
				ItemExample.Criteria criteria1 = itemExample.createCriteria();
				criteria1.andIdEqualTo(orderItem.getItemId());
				List<Item> itemList = itemMapper.selectByExample(itemExample);

				//排行榜
				redisTemplate.boundZSetOps("itemSort").incrementScore(itemList.get(0).getId(),1);
			}
		}



		//清除缓存中的paylog
		redisTemplate.boundHashOps("payLog").delete(payLog.getUserId());
}


}
