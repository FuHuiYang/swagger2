package com.yang.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.config
 * @Description: TODO
 * @date Date : 2019年09月18日 10:02
 */
@Configuration
@MapperScan(basePackages = "com.yang.mybatis.mapper3",sqlSessionFactoryRef = "sqlSessionFactory3",sqlSessionTemplateRef = "sqlSessionTemplate3")
public class MyBatisConfigThree {
    @Resource(name = "dsThree")
    DataSource dsThree;

    @Bean
    SqlSessionFactory sqlSessionFactory3() {
        SqlSessionFactory sessionFactory = null;
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dsThree);
            sessionFactory = bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
    @Bean
    SqlSessionTemplate sqlSessionTemplate3() {
        return new SqlSessionTemplate(sqlSessionFactory3());
    }
}
