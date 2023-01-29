package gov.iti.jets.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.sql.PooledConnection;

public class DBManagement {
    private static DBManagement dbm;
    private MysqlConnectionPoolDataSource poolDataSource;
    private PooledConnection poolConn;

    private DBManagement() {
        Properties prop = new Properties();
        FileInputStream in;
        try {
            in = new FileInputStream(new File(getClass().getResource("/app.properties").getPath()));
            prop.load(in);
            poolDataSource = new MysqlConnectionPoolDataSource();

            poolDataSource.setURL(prop.getProperty("MYSQL_DB_URL"));
            poolDataSource.setUser(prop.getProperty("MYSQL_DB_USERNAME"));
            poolDataSource.setPassword(prop.getProperty("MYSQL_DB_PASSWORD"));

            poolConn = poolDataSource.getPooledConnection();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (dbm != null && poolDataSource != null && poolConn != null) {
            try {
                return poolConn.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DBManagement getInstance() {
        if (dbm == null) {
            dbm = new DBManagement();
        }
        return dbm;
    }
}
