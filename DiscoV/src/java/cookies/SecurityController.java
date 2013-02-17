package cookies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;

public class SecurityController {
       
    
   public static String getCookieValue(Cookie[] cookies) {
    
    String username_cookie_value = null;
    String auth_token = null;
    
    if (cookies != null) {
    for(int i=0; i<cookies.length; i++) {
      Cookie cookie = cookies[i];
      
      // check for username cookie
      if ("username".equals(cookie.getName())) {
           username_cookie_value = cookie.getValue();
      }
      
      // check auth cookie
      if ("auth_cookie".equals(cookie.getName())) {
        auth_token = cookie.getValue();
      }
        
    }
    }
    // do logic
    // if found username check database for auth string
   if ( username_cookie_value != null) {
     
    Context envContext;
     try {

       envContext = new InitialContext();
       DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/m_users");
       Connection con = ds.getConnection();
       String findUser = "SELECT * FROM cookies WHERE username = ? AND authstring = ?";
       PreparedStatement prepStmt = con.prepareStatement(findUser);
       prepStmt.setString(1, username_cookie_value);
       prepStmt.setString(2, auth_token);
       ResultSet result = prepStmt.executeQuery();
       
       // if auth token matches return username
       if (result.next()){
         return (username_cookie_value);
       }
       else {
         return (null);
       }

     } catch (SQLException e) {

     } catch (NamingException e) {
     }
      
    }
    
    return(null);
  }
   
 
   
}