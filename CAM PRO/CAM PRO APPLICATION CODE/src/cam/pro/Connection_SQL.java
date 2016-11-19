package cam.pro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection_SQL 
{
    public static Connection connectId()
    {
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cam_pro","root","");
            return con;
        }
        catch(ClassNotFoundException | SQLException e)
        {
            return null;
        }
    }
}
