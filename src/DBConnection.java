
import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
