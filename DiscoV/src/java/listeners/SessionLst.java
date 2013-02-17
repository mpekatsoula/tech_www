package listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;

public class SessionLst implements HttpSessionListener {

    /**
     *
     * @param se
     */
    @Override
    synchronized public void sessionCreated(HttpSessionEvent se) {
               
        
        

    }

    /**
     *
     * @param se
     */
    @Override
    synchronized public void sessionDestroyed(HttpSessionEvent se) {

        String username;
        HttpSession session = se.getSession();
        
        username = (String) session.getAttribute("remember_me");
        
        if (username != null) {
            
          Context envContext;
          try {

            envContext = new InitialContext();
            DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/m_users");
            Connection con = ds.getConnection();
            String dropUser = "DELETE FROM cookies WHERE username = ?";
            PreparedStatement prepStmt = con.prepareStatement(dropUser);
            prepStmt.setString(1, username);
            int result = prepStmt.executeUpdate();


          } catch (SQLException e) {
     
          } catch (NamingException e) {
            
          }
        }
    }


   

}
