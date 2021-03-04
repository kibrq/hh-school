package ru.hh.school.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.common.properties.FileSettings;
import ru.hh.nab.common.properties.PropertiesUtils;
import ru.hh.nab.datasource.DataSourceFactory;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.hibernate.NabHibernateProdConfig;
import ru.hh.nab.hibernate.NabSessionFactoryBean;
import ru.hh.nab.starter.NabCommonConfig;
import ru.hh.school.dao.EmployerDao;
import ru.hh.school.resource.EmployerResource;
import ru.hh.school.resource.ExampleResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;


@Configuration
@Import({
        // import your beans here
        NabHibernateProdConfig.class,
        ExampleResource.class,
        NabCommonConfig.class,
        EmployerResource.class,
        EmployerDao.class
})
public class CommonConfig {


    @Bean
    public DataSource dataSource(DataSourceFactory dataSourceFactory, FileSettings fileSettings) {
        return dataSourceFactory.create("master", false, fileSettings);
    }

    @Bean
    public Properties properties() throws IOException {
        return PropertiesUtils.fromFilesInSettingsDir("service.properties");
    }


    @Bean
    public MappingConfig mappingConfig() {
        MappingConfig mappingConfig = new MappingConfig();
        mappingConfig.addPackagesToScan("ru.hh.school.entity");
        return mappingConfig;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory(NabSessionFactoryBean sessionFactoryBean) {
        return sessionFactoryBean.getObject();
    }

}
