package com.spring.tx_xml_service;

        import java.util.List;

/**
 * Created by Administrator on 2016-12-15.
 */
public interface Cashier {
    public void checkout(String username, List<String> isbns);
}
