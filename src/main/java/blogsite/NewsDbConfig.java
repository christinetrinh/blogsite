package blogsite;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "newsEntityManagerFactory",
        transactionManagerRef = "newsTransactionManager",
        basePackages = { "blogsite.news.repository" }
)
public class NewsDbConfig {

    @Bean(name = "newsDataSource")
    @ConfigurationProperties(prefix = "spring.news")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "newsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    newsEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("newsDataSource") DataSource dataSource
    ) {
        return
                builder
                        .dataSource(dataSource)
                        .packages("blogsite.news.entity")
                        .persistenceUnit("news")
                        .build();
    }
    @Bean(name = "newsTransactionManager")
    public PlatformTransactionManager newsTransactionManager(
            @Qualifier("newsEntityManagerFactory") EntityManagerFactory
                    newsEntityManagerFactory
    ) {
        return new JpaTransactionManager(newsEntityManagerFactory);
    }
}
