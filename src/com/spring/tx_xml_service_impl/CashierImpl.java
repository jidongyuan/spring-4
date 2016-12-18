package com.spring.tx_xml_service_impl;

import com.spring.tx_xml_service.BookShopService;
import com.spring.tx_xml_service.Cashier;

import java.util.List;

/**
 * Created by Administrator on 2016-12-15.
 */

public class CashierImpl implements Cashier {
    private BookShopService bookShopService;

    public void setBookShopService(BookShopService bookShopService) {
        this.bookShopService = bookShopService;
    }

    @Override
    public void checkout(String username, List<String> isbns) {
        for(String isbn:isbns){
            bookShopService.purchase(username,isbn);
        }
    }
}
