package com.spring.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-11-29.
 */
public class JDBCTest {
    private ApplicationContext ctx = null;
    private JdbcTemplate jdbcTemplate;
    private StudentDao studentDao;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        jdbcTemplate = (JdbcTemplate)ctx.getBean("jdbcTemplate");
        studentDao = ctx.getBean(StudentDao.class);
        namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
    }

    /**
     *使用具名参数时，可以使用update(sql,sqlParameterSource)方法进行更新操作，
     * 1.sql语句中的参数名与类的属性一致
     * 2.使用SqlParameterSource的实现类BeanPropertySqlParameterSource作为参数
     */
    @Test
    public void testNamedParameterJdbcTemplate2(){
        String sql = "insert into student(name,password,classid) values(:student_name,:password,:classid)";
        Student student = new Student();
        student.setPassword("123");
        student.setStudent_name("jidongyuan");
        student.setClassid(3);
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(student);
        namedParameterJdbcTemplate.update(sql,sqlParameterSource);
    }

    /**
     *可以为参数起名字
     * 1.优点：若有多个参数，则不在对应位置，直接对应参数名，便于维护
     * 2.缺点：较为麻烦
     */
    @Test
    public void testNamedParameterJdbcTemplate(){
        String sql = "insert into user(name,password,classid) values(:name,:password,:classid)";
        Map<String,Object> map = new HashMap<>();
        map.put("_name","jidongyuan");
        map.put("_password","123456");
        namedParameterJdbcTemplate.update(sql,map);
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
