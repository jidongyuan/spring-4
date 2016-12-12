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
     *ʹ�þ�������ʱ������ʹ��update(sql,sqlParameterSource)�������и��²�����
     * 1.sql����еĲ��������������һ��
     * 2.ʹ��SqlParameterSource��ʵ����BeanPropertySqlParameterSource��Ϊ����
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
     *����Ϊ����������
     * 1.�ŵ㣺���ж�����������ڶ�Ӧλ�ã�ֱ�Ӷ�Ӧ������������ά��
     * 2.ȱ�㣺��Ϊ�鷳
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
     *��ȡ�����е�ֵ����ͳ�Ʋ�ѯ
     * ʹ�� org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, java.lang.Class)
     */
    @Test
    public void testQueryForObject2(){
        String sql = "select count(id) from student";
        long count = jdbcTemplate.queryForObject(sql,long.class);
        System.out.println(count);
    }

    /**
     *�鵽ʵ����ļ���
     */
    @Test
    public void testQueryForList(){
        String sql = "select id , name student_name ,password from student where id > ?";
        org.springframework.jdbc.core.RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
        List<Student> student = jdbcTemplate.query(sql,rowMapper,1);

        System.out.println(student);
    }

    /**
     *�����ݿ��ȡһ����¼��ʵ�ʵõ���Ӧ��һ������
     * ע�ⲻ�ǵ��� queryForObject(String sql,Class<Student> requiredType,Object...args);
     * ����Ҫ���� queryForObject(String sql,RowMaper<Student> rowMaper,Object...args);
     * 1.����RowMaperָ�����ȥӳ��ָ����������У����õ�ʵ����ΪBeanPropertyRowMapper
     * 2.ʹ��sql���еı�����������������������ӳ�䣬���磺name student_name
     * ��֧�ּ������ԣ�JdbcTemplate������һ��jdbcС���ߣ�������orm���
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
     *@Date2016-11-30���� 10:34
     *
     * ִ���������£�������updata��insert��delete
     * ���һ��������object[]��list���ͣ���Ϊ�޸�һ����¼��Ҫһ��object�����飬��������Ҫ���object����
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
