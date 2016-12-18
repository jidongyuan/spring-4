package com.spring.tx_xml;

import com.spring.tx_xml_service.BookShopService;
import com.spring.tx_xml_service.Cashier;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * Created by Administrator on 2016-12-12.
 */
public class SpringTransactionTest {
    private ApplicationContext ctx = null;
    private BookShopDao bookShopDao = null;
    private BookShopService bookShopService = null;
    private Cashier cashier = null;

    {
        ctx = new ClassPathXmlApplicationContext("applicationContext-tx-xml.xml");
        bookShopDao = ctx.getBean(BookShopDao.class);
        bookShopService = ctx.getBean(BookShopService.class);
        cashier = ctx.getBean(Cashier.class);
    }

    @Test
    public void testTransactionalPropagation(){
        cashier.checkout("AA", Arrays.asList("1001", "1002"));
    }

    @Test
    public void testBookShopService(){
        bookShopService.purchase("AA","1001");
    }

}
