package ua.klunniy.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Этот файл заменяет ApplicationContextMVC.xml, конфигурация Spring приложения,
 * бины, ComponentScan, настройка Thymeleaf
 */
@Configuration
@EnableWebMvc
@ComponentScan("ua.klunniy.spring")
@PropertySource({"classpath:database.properties",
        "classpath:hibernate.properties"})
@EnableJpaRepositories("ua.klunniy.spring.repositories")
@EnableTransactionManagement
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final Environment environment;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
//        dataSource.setUrl(environment.getProperty("url"));
//        dataSource.setUsername(environment.getProperty("username_value"));
//        dataSource.setPassword(Objects.requireNonNull(environment.getProperty("password")));
//        return dataSource;
//    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("driver"));
        dataSource.setUrl(environment.getRequiredProperty("url"));
        dataSource.setUsername(environment.getRequiredProperty("username_value"));
        dataSource.setPassword(environment.getRequiredProperty("password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    //  Это для hibernate 3 метода
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        return properties;
    }

    //  Это для hibernate session - sessionFactory
    @Bean(name = "hibernateSessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ua.klunniy.spring.models");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean(name = "hibernateTransactionManager")
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

//  Это для jpa entity manager - entity manager factory  - sessionFactory
 //Настройки для Hibernate EntityManagerFactory
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("ua.klunniy.spring.models");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

//    @Bean(name = "jpaTransactionManager")
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//
//        return transactionManager;
//    }

}




//    @Bean(name = "hibernateEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean hibernateEntityManagerFactory(DataSource dataSource) {
//        // Конфигурация Hibernate EntityManagerFactory
//    }


// Настройки для Hibernate
//    @Bean(name = "hibernateTransactionManager")
//    public PlatformTransactionManager hibernateTransactionManager(
//            @Qualifier("hibernateEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new org.springframework.orm.hibernate5.HibernateTransactionManager(entityManagerFactory.unwrap(org.hibernate.SessionFactory.class));
//    }
//
//    // Настройки для JPA
//    @Bean(name = "jpaTransactionManager")
//    public PlatformTransactionManager jpaTransactionManager(
//            @Qualifier("jpaEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//    // Настройки для Hibernate EntityManagerFactory
//    @Bean(name = "hibernateEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean hibernateEntityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("ua.klunniy.spring.models"); // Пакеты, в которых находятся сущности Hibernate
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        Properties hibernateProperties = new Properties();
//        // Настройки Hibernate, если нужны
//        em.setJpaProperties(hibernateProperties);
//
//        return em;
//    }
//
//    // Настройки для JPA EntityManagerFactory
//    @Bean(name = "jpaEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("ua.klunniy.spring.models"); // Пакеты, в которых находятся сущности JPA
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); // Используем Hibernate JPA Vendor Adapter
//
//        // Дополнительные настройки JPA, если нужны
//
//        return em;
//    }



