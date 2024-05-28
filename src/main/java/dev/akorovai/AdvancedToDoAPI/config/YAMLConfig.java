package dev.akorovai.AdvancedToDoAPI.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring")
@Data
public class YAMLConfig {

    private H2 h2 = new H2();
    private Datasource datasource = new Datasource();
    private Profiles profiles = new Profiles();
    private Jpa jpa = new Jpa();
    private Springdoc springdoc = new Springdoc();

    @Data
    public static class H2 {
        private Console console = new Console();

        @Data
        public static class Console {
            private boolean enabled;
        }
    }

    @Data
    public static class Datasource {
        private String url;
        private String driverClassName;
        private String username;
        private String password;
    }

    @Data
    public static class Profiles {
        private String active;
    }

    @Data
    public static class Jpa {
        private boolean showSql;
        private Hibernate hibernate = new Hibernate();
        private String databasePlatform;
        private boolean openInView;
        private boolean deferDatasourceInitialization;

        @Data
        public static class Hibernate {
            private String ddlAuto;
        }
    }

    @Data
    public static class Springdoc {
        private SwaggerUi swaggerUi = new SwaggerUi();

        @Data
        public static class SwaggerUi {
            private boolean disableSwaggerDefaultUrl;
        }
    }
}
