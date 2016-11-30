package com.spring.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-29.
 */
public class JDBCTest {
    private ApplicationContext ctx = null;
    private JdbcTemplate jdbcTemplate;
    {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        jdbcTemplate = (JdbcTemplate)ctx.getBean("jdbcTemplate");
    }

    /**
     *@Author jidongyuan
     *@Date2016-11-30下午 10:34
     *
     * 执行批量更新：批量的updata、insert、delete
     * 最后一个参数是object[]的list类型：因为修改一条记录需要一个object的数组，多条就需要多个object数组
     */
    @Test
    public void testBatchUpdata(){
        String sql = "INSERT INTO user(name,password) VALUE(?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"AA","123456"});
        batchArgs.add(new Object[]{"BB","123456"});
        batchArgs.add(new Object[]{"CC","123456"});
        jdbcTemplate.batchUpdate(sql,batchArgs);
    }

    @Test
    public void testUpdate(){
        String sql = "UPDATE user set name = ? where id = ?";
        jdbcTemplate.update(sql,"jdy",1);
    }

    @Test
    public void  testDataSource() throws SQLException{
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }
}
