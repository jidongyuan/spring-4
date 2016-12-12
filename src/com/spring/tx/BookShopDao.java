package com.spring.tx;

/**
 * Created by Administrator on 2016-12-12.
 */
public interface BookShopDao {

    /*根据书号获取书的单价*/
    public int findBookPriceByIsbn(String isbn);

    /*更新书的库存，使书号对应的库存-1*/
    public void updateBookStock(String isbn);

    /*更新用户账户余额：使username的balance - price*/
    public void updateUserAccount(String username,int price);
}
