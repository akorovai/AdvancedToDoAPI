package dev.akorovai.AdvancedToDoAPI;

import dev.akorovai.AdvancedToDoAPI.config.YAMLConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan({"dev.akorovai.AdvancedToDoAPI.entity"})
@EnableJpaRepositories({"dev.akorovai.AdvancedToDoAPI.repository"})
@SpringBootApplication

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdvancedToDoApiApplication implements CommandLineRunner {

    private final YAMLConfig myConfig;

    public static void main(String[] args) {
        SpringApplication.run(AdvancedToDoApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("H2 Configuration:");
        System.out.println("Console enabled: " + myConfig.getH2().getConsole().isEnabled());

        System.out.println("\nDatasource Configuration:");
        System.out.println("URL: " + myConfig.getDatasource().getUrl());
        System.out.println("Driver Class Name: " + myConfig.getDatasource().getDriverClassName());
        System.out.println("Username: " + myConfig.getDatasource().getUsername());
        System.out.println("Password: " + myConfig.getDatasource().getPassword());
        System.out.println("Hikari Configuration:");
        System.out.println("  Connection Timeout: " + myConfig.getDatasource().getHikari().getConnectionTimeout());
        System.out.println("  Maximum Pool Size: " + myConfig.getDatasource().getHikari().getMaximumPoolSize());
        System.out.println("  Minimum Idle: " + myConfig.getDatasource().getHikari().getMinimumIdle());
        System.out.println("  Idle Timeout: " + myConfig.getDatasource().getHikari().getIdleTimeout());

        System.out.println("\nActive Profile:");
        System.out.println(myConfig.getProfiles().getActive());

        System.out.println("\nSwagger-UI Configuration:");
        System.out.println("Disable Swagger Default URL: " + myConfig.getSpringdoc().getSwaggerUi().isDisableSwaggerDefaultUrl());
        System.out.println("Path: " + myConfig.getSpringdoc().getSwaggerUi().getPath());

        System.out.println("\nJPA Configuration:");
        System.out.println("Show SQL: " + myConfig.getJpa().isShowSql());
        System.out.println("Database Platform: " + myConfig.getJpa().getDatabasePlatform());
        System.out.println("Open In View: " + myConfig.getJpa().isOpenInView());
        System.out.println("Defer Datasource Initialization: " + myConfig.getJpa().isDeferDatasourceInitialization());

        System.out.println("\nHibernate Configuration:");
        System.out.println("DDL Auto: " + myConfig.getJpa().getHibernate().getDdlAuto());
        System.out.println("Hibernate Properties:");
        System.out.println("  JDBC Batch Size: " + myConfig.getJpa().getHibernate().getProperties().getJdbcBatchSize());
        System.out.println("  Format SQL: " + myConfig.getJpa().getHibernate().getProperties().isFormatSql());
    }
}
