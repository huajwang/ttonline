package com.troika.emall.config;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan
public class JpaConfig {
	
	@Bean(destroyMethod = "shutdown")
	@Profile("dev")
	public DataSource embeddedDataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:schema.sql")
				.addScript("classpath:test-data.sql")
				.build();
	}
	
	  @Bean
	  @Profile("production")
	  public DataSource dataSource() {
		  DruidDataSource dataSource = new DruidDataSource();
		  dataSource.setDriverClassName("com.mysql.jdbc.Driver"); 
		  // 共享家， 部署在拓意客公众号上
		  dataSource.setUrl("jdbc:mysql://localhost:3306/emall?useUnicode=true&characterEncoding=utf-8&autoReconnect=true");
		  // dataSource.setUrl("jdbc:mysql:replication://120.27.98.10:3306,120.55.81.69:3306/wbj_im_sys_kf_copy?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
		  dataSource.setUsername("root");
		  dataSource.setPassword("Gold1Silver2");
		  dataSource.setMaxActive(5);
		  return dataSource;
	  }

	@Bean
  public LocalContainerEntityManagerFactoryBean emf(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource);
    emf.setPersistenceUnitName("emall");
    emf.setJpaVendorAdapter(jpaVendorAdapter);
    emf.setPackagesToScan("com.troika.emall.model","com.troika.groupon.model","com.troika.vtools.model");
    return emf;
  }
  
  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setDatabase(Database.MYSQL);
    adapter.setShowSql(false);
    adapter.setGenerateDdl(false);
    adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
    return adapter;
  }
  

  @Configuration
  @EnableTransactionManagement
  public static class TransactionConfig implements TransactionManagementConfigurer {
    @Inject
    private EntityManagerFactory emf;
    
    public PlatformTransactionManager annotationDrivenTransactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(emf);
      return transactionManager;
    }    
  }
}
