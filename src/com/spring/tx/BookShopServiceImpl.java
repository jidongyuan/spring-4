package com.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016-12-12.
 */

@Service
public class BookShopServiceImpl implements BookShopService {

    @Autowired
    private BookShopDao bookShopDao;



    //添加事务注解
    //使用propagation指定事务的传播行为，即当前的事务方法被另一个事务方法调用时，如何使用事务，默认取值为REQUIRED,即使用调用方法的事务
    // REQUIRES_NEW 事务自己的事务，调用的事务方法的事务被挂起
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void purchase(String username, String isbn) {
        /*1.获取书的单价*/
        int price = bookShopDao.findBookPriceByIsbn(isbn);
        /*2.更新书的库存*/
        bookShopDao.updateBookStock(isbn);
        /*3.更新用户余额*/
        bookShopDao.updateUserAccount(username,price);
    }
}
