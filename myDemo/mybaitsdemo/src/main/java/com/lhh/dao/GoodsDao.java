package com.lhh.dao;

import com.lhh.pojo.Goods;
import org.springframework.stereotype.Repository;

/**
 * @author lihonghao
 * @date 2021/4/12 16:02
 */
@Repository
public interface GoodsDao {
	// 更新库存
	int updateGoods(Goods goods);

	// 查询商品信息
	Goods selectGoods(Integer gid);
}
