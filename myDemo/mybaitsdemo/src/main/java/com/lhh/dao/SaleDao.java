package com.lhh.dao;

import com.lhh.pojo.Sale;
import org.springframework.stereotype.Repository;

/**
 * @author lihonghao
 * @date 2021/4/12 15:57
 */
@Repository
public interface SaleDao {
	// 增加销售记录
	int insertSale(Sale sale);

}
