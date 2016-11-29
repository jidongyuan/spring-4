package com.spring.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016-11-29.
 */
public class JDBCTest {
    private ApplicationContext ctx = null;
    {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void  testDataSource() throws SQLException{
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }
}
