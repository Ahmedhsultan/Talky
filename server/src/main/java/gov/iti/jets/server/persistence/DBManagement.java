package gov.iti.jets.server.persistence;


import javax.sql.PooledConnection;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;


public class DBManagement {
    private static DBManagement dbm;
    private MysqlConnectionPoolDataSource poolDataSource;
    private PooledConnection poolConn;

    private DBManagement() {
        Properties prop = new Properties();
        FileInputStream in;
        try {
//            in = new FileInputStream(new File(getClass().getClassLoader().getResource("app.properties").getPath()));
//            prop.load(in);
            poolDataSource = new MysqlConnectionPoolDataSource();

            poolDataSource.setURL("jdbc:mysql://localhost:3306/talkey?characterEncoding=latin1");
            poolDataSource.setUser("root");
            poolDataSource.setPassword("root");

            poolConn = poolDataSource.getPooledConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        DBManagement dbm = getInstance();

        if (dbm != null && dbm.poolDataSource != null && dbm.poolConn != null) {
            try {
                return dbm.poolConn.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static DBManagement getInstance() {
        if (dbm == null) {
            dbm = new DBManagement();
        }
        return dbm;
    }
}
