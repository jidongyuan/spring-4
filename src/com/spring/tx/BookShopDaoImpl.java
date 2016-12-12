package com.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016-12-12.
 */

@Repository
public class BookShopDaoImpl implements BookShopDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int findBookPriceByIsbn(String isbn) {
        String sql = "select price from book where isbn = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,isbn);
    }

    @Override
    public void updateBookStock(String isbn) {
        /*�����Ŀ���Ƿ��㹻*/
        String sql2 = "select stock from book_stock where isbn = ?";
        int stock = jdbcTemplate.queryForObject(sql2,Integer.class,isbn);
        if(stock == 0){
            throw new BookStockException("��治�㣡");
        }
        String sql = "UPDATE book_stock set stock = stock - 1 where isbn = ?";
        jdbcTemplate.update(sql,isbn);
    }

    @Override
    public void updateUserAccount(String username, int price) {
        /*�������Ƿ��㹻*/
        String sql2 = "select balance from account where username = ?";
        int balance = jdbcTemplate.queryForObject(sql2,Integer.class,username);
        if(balance < price){
            throw new UserAccountException("�˻����㣡");
        }

        String sql = "update account set balance = balance - ? where username = ?";
        jdbcTemplate.update(sql,price,username);
    }
}
