package ru.hh.school;

import ru.hh.nab.common.properties.PropertiesUtils;
import ru.hh.nab.hibernate.NabHibernateProdConfig;
import ru.hh.nab.starter.NabApplication;
import ru.hh.school.config.CommonConfig;
import ru.hh.school.config.ProdConfig;

public class App {

    public static NabApplication createApplication() {
        return NabApplication
                .builder()
                .configureJersey()
                .bindToRoot()
                .build();
    }

    public static void main(String[] args) {
        createApplication().run(ProdConfig.class);
    }
}
