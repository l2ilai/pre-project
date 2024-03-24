package overridetech.jdbc.jpa.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import overridetech.jdbc.jpa.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    public static SessionFactory sessionFactory;

    public Util() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = getPostgresConfiguration();
            sessionFactory = createSessionFactory(configuration);
        }
        return sessionFactory;
    }
    private static Configuration getPostgresConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres1");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "pass");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }
    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

        public static Connection getPostgresConnection() {
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres1";
            String userName = "postgres";
            String userPass = "pass";
            Connection connection = DriverManager.getConnection(url, userName, userPass);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
