package com.spring.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
    private StudentDao studentDao;
    {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        jdbcTemplate = (JdbcTemplate)ctx.getBean("jdbcTemplate");
        studentDao = ctx.getBean(StudentDao.class);
    }

    @Test
    public void testStudentDao(){
        System.out.println(studentDao.get(8));
    }

    /**
     *获取单个列的值或做统计查询
     * 使用 org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, java.lang.Class)
     */
    @Test
    public void testQueryForObject2(){
        String sql = "select count(id) from student";
        long count = jdbcTemplate.queryForObject(sql,long.class);
        System.out.println(count);
    }

    /**
     *查到实体类的集合
     */
    @Test
    public void testQueryForList(){
        String sql = "select id , name student_name ,password from student where id > ?";
        org.springframework.jdbc.core.RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
        List<Student> student = jdbcTemplate.query(sql,rowMapper,1);

        System.out.println(student);
    }

    /**
     *从数据库获取一条记录，实际得到对应的一个对象
     * 注意不是调用 queryForObject(String sql,Class<Student> requiredType,Object...args);
     * 而需要调用 queryForObject(String sql,RowMaper<Student> rowMaper,Object...args);
     * 1.其中RowMaper指定如何去映射指定结果集的行，常用的实现类为BeanPropertyRowMapper
     * 2.使用sql中列的别名完成列名和类的属性名的映射，例如：name student_name
     * 不支持级联属性，JdbcTemplate到底是一个jdbc小工具，而不是orm框架
     *
     */
    @Test
    public void testQueryForObject(){
        String sql = "select id , name student_name ,password from student where id = ?";
        org.springframework.jdbc.core.RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
        Student student = jdbcTemplate.queryForObject(sql,rowMapper,1);

        System.out.println(student);
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
