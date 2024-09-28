package com.whomade.kycarrots.config;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-30
 */
@Configuration
@MapperScan(basePackages = "com.whomade.kycarrots.repository.mybatis")
@EnableTransactionManagement
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        // Type Aliases 설정
        sessionFactory.setTypeAliasesPackage("com.whomade.kycarrots.entity");

        // 또는 직접 Type Aliases 설정
        Map<String, Class<?>> typeAliases = new HashMap<>();
        typeAliases.put("dataMap", DataMap.class);
        typeAliases.put("atFileVO", AtFileVO.class);
        sessionFactory.setTypeAliases(typeAliases.values().toArray(new Class[0]));

        // Mapper XML 파일 위치 설정
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml")
        );

        // MyBatis Configuration 설정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true); // map-underscore-to-camel-case 설정
        configuration.setJdbcTypeForNull(org.apache.ibatis.type.JdbcType.NULL); // jdbcTypeForNull 설정
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
