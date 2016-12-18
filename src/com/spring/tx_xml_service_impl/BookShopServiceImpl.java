package com.spring.tx_xml_service_impl;

/**
 * Created by Administrator on 2016-12-12.
 */

public class BookShopServiceImpl implements com.spring.tx_xml_service.BookShopService {

    private com.spring.tx_xml.BookShopDao bookShopDao;

    public void setBookShopDao(com.spring.tx_xml.BookShopDao bookShopDao) {
        this.bookShopDao = bookShopDao;
    }

    @Override
    public void purchase(String username, String isbn) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*1.获取书的单价*/
        int price = bookShopDao.findBookPriceByIsbn(isbn);
        /*2.更新书的库存*/
        bookShopDao.updateBookStock(isbn);
        /*3.更新用户余额*/
        bookShopDao.updateUserAccount(username,price);
    }
}
