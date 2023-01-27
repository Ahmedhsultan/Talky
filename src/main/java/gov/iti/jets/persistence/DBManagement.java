package gov.iti.jets.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import com.mysql.cj.jdbc.MysqlDataSource;

public class DBManagement {
    private static  DBManagement dbm;
    private MysqlDataSource dataSource;

    private DBManagement() {
        Properties prop = new Properties();
        FileInputStream in;
        try {
            in = new FileInputStream(new File(getClass().getResource("/app.properties").getPath()));
            prop.load(in);
            dataSource = new MysqlDataSource();
            System.out.println("hi");
            dataSource.setURL(prop.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(prop.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(prop.getProperty("MYSQL_DB_PASSWORD"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public Connection getConnection()
        {
            if(dbm!=null && dataSource!=null) {
                try {
                    return dataSource.getConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public static DBManagement getInstance()
    {
        if(dbm== null)
        {
            dbm = new DBManagement();
        }
        return dbm;
    }

}
