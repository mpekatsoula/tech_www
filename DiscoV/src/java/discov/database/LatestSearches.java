package discov.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class LatestSearches {
  
  
  public ArrayList Latest_Searches(HttpServletRequest request) {
    
    /* Get username */
    Cookie cookie = null;
    String username = null;
    Cookie[] cookies = request.getCookies();
    for(int i=0; i < cookies.length; i++) {
       cookie = cookies[i];
      if ("username".equals(cookie.getName())) {
        username = cookie.getValue();
      }
    }
    
    ArrayList searches = new ArrayList();
    
    try {
                    
      Context envContext = new InitialContext();
                                    
      DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/m_users");
      Connection con = ds.getConnection();
               
      /* Search for user's searches */
      String findUser = "SELECT * FROM searches WHERE username = ?";
      PreparedStatement prepStmt = con.prepareStatement(findUser);
      prepStmt.setString(1, username);
      ResultSet result = prepStmt.executeQuery();
      int k =0;
      
      result.getRow();
      /* Get all results and store them */
      while (result.next()) {
        searches.add(result.getString(2));
        searches.add(result.getString(3));
      }

      } catch (SQLException e) {
        //  out.print(e);
      } catch (NamingException e) {
        //  out.print(e);
      }
    
    return searches;
    
  }
  
  
}
