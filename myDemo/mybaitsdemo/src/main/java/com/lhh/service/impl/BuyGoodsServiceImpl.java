package com.lhh.service.impl;

import com.lhh.dao.GoodsDao;
import com.lhh.dao.SaleDao;
import com.lhh.excep.NotEnoughException;
import com.lhh.pojo.Goods;
import com.lhh.pojo.Sale;
import com.lhh.service.BuyGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lihonghao
 * @date 2021/4/12 16:19
 */
public class BuyGoodsServiceImpl implements BuyGoodsService {
	@Autowired
	private SaleDao saleDao;
	@Autowired
	private GoodsDao goodsDao;
	@Override
	public void buy(Integer goodId, Integer nums) {
		// 添加记录
		Sale sale = new Sale();
		sale.setGid(goodId);
		sale.setNums(nums);
		saleDao.insertSale(sale);
		// 更新库存

		Goods goods = goodsDao.selectGoods(goodId);
		if(goods == null){
			throw  new NotEnoughException("商品不存在，商品id = " + goodId);
		}else if (goods.getAmount() < nums){
			throw new NotEnoughException("商品不足，商品id = " + goodId);
		}
		// 修改库存
		Goods buyGoods  = new Goods();
		buyGoods.setId(goodId);
		buyGoods.setAmount(nums);
		goodsDao.updateGoods(buyGoods);
	}
}
