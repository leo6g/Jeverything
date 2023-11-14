package only.leo.wfm.core.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: leo
 * @Email: leosfox@foxmail.com
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan("only.leo.wfm.core.dao")

public class SessionFactoryConfig implements TransactionManagementConfigurer{
    //获得驱动
    private static String DRIVER = "org.h2.Driver";
    //获得url
    private static String URL = "jdbc:h2:SYSTEM/data/wfm";
    //获得连接数据库的用户名
    private static String USER = "sa";
    //获得连接数据库的密码
    private static String PASS = "sa";
    /** * mybatis 配置路径 */
    private static String MYBATIS_CONFIG = "mybatis/mybatis-config.xml";
    private static String[] MAPPER_FILE_LOCATIONS = {"classpath:/mappers/*.xml", "classpath:/mappers/**/*.xml"};
    @Autowired
    private DataSource dataSource;

    private String typeAliasPackage = "com.xxx.xxx.mybati.model";

    /**
     *创建sqlSessionFactoryBean 实例
     * 并且设置configtion 如驼峰命名.等等
     * 设置mapper 映射路径
     * 设置datasource数据源
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    @DependsOn("h2server")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        /** 设置mybatis configuration 扫描路径 */
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        setMapperLocations(sqlSessionFactoryBean);
        /** 设置datasource */
        sqlSessionFactoryBean.setDataSource(dataSource);
        /** 设置typeAlias 包扫描路径 */
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
        return sqlSessionFactoryBean;
    }
    private void setMapperLocations(SqlSessionFactoryBean sqlSessionFactoryBean) {
        List<Resource> resourceList = new ArrayList<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            for (String resourceLocation : MAPPER_FILE_LOCATIONS) {
                Resource[] resources = resolver.getResources(resourceLocation);
                for (Resource resource : resources) {
                    resourceList.add(resource);
                }
            }
            sqlSessionFactoryBean.setMapperLocations(resourceList.toArray(new Resource[resourceList.size()]));
        } catch (Exception e) {
        }
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    @Bean
    @Primary
    public DataSource druidDataSource(){
        JdbcConnectionPool cp = JdbcConnectionPool.create(
                URL, USER, PASS);
        cp.setMaxConnections(100);
        cp.setLogWriter(null);
        return cp;
    }
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
