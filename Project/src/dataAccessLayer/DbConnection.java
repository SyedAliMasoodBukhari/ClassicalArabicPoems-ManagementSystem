package dataAccessLayer;

import java.sql.Connection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.*;

public class DbConnection {

    private static Connection connection = null;
    private static String DB_URL = "";
    private static String DB_USER = "";
    private static String DB_PASSWORD = "";
    private static final Logger logger = LogManager.getLogger(PoemsDAO.class);

    private DbConnection() {
        super();
    }

    public static final Connection getConnection() {
        if (connection == null) {
            try {
                getDbCredentials();
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                logger.info("Getting connection of db for the first time.");
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return connection;
    }

    private static void getDbCredentials() {
        Properties p = new Properties();
        String configFilePath = "config\\config.properties";
        try {
            FileInputStream configFileReader = new FileInputStream(new File(configFilePath));
            p.load(configFileReader);
            configFileReader.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("Getting credentials of db from properties.");
        DB_URL = p.getProperty("db.url");
        DB_USER = p.getProperty("db.user");
        DB_PASSWORD = p.getProperty("db.password");
    }
}
