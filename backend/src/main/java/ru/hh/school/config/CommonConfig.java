package ru.hh.school.config;

import org.hibernate.SessionFactory;
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
import ru.hh.school.dao.GenericDao;
import ru.hh.school.resource.FavoritesVacancyResource;
import ru.hh.school.resource.VacancyResource;
import ru.hh.school.service.*;
import ru.hh.school.resource.EmployerResource;
import ru.hh.school.resource.FavoritesEmployerResource;
import ru.hh.school.util.PopularityDecider;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;


@Configuration
@Import({
        // import your beans here
        NabHibernateProdConfig.class,
        NabCommonConfig.class,
        EmployerResource.class,
        FavoritesEmployerResource.class,
        VacancyResource.class,
        FavoritesVacancyResource.class,
        EmployerOuterService.class,
        VacancyOuterService.class,
        FavoritesVacancyService.class,
        FavoritesEmployerService.class,
        PopularityDecider.class,
        GenericDao.class
})
public class CommonConfig {

    public CommonConfig() {}

    @Bean
    public DataSource dataSource(DataSourceFactory dataSourceFactory, FileSettings fileSettings) {
        return dataSourceFactory.create("master", false, fileSettings);
    }

    @Bean
    public Properties properties() throws IOException {
        return PropertiesUtils.fromFilesInSettingsDir("service.properties");
    }

    @Bean("popularity_threshold")
    public Integer popularityThreshold(FileSettings fileSettings) {
        return fileSettings.getInteger("popularity.threshold");
    }

    @Bean
    public MappingConfig mappingConfig() {
        MappingConfig mappingConfig = new MappingConfig();
        mappingConfig.addPackagesToScan("ru.hh.school.entity");
        return mappingConfig;
    }

    @Bean
    public SessionFactory sessionFactory(NabSessionFactoryBean sessionFactoryBean) {
        return sessionFactoryBean.getObject();
    }

}
