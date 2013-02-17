package discov.database;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.sql.DataSource;

public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{
            
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            HttpSessionEvent se = null;
        
        try {
                    
            Context envContext = new InitialContext();
                                    
            DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/m_users");
            Connection con = ds.getConnection();
            
            String username = request.getParameter("username");
            String password = request.getParameter("password");
                                              
             /* Check if user already exists to the database and login */
            String findUser = "SELECT * FROM users WHERE username = ? AND password = SHA1(?)";
            PreparedStatement prepStmt = con.prepareStatement(findUser);
            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
            ResultSet result = prepStmt.executeQuery();
            
            if ( result.next() ){
              
              HttpSession session = request.getSession();
              
              // Get remember me checkbox
              String[] remember_me;
              remember_me = request.getParameterValues("remember_me");
             
              // create the auth cookie: SHA256(username + password + IP)
              MessageDigest md = null;
              try {
                md = MessageDigest.getInstance("SHA-256");
              } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
              }
              
              String text = username + password + request.getRemoteAddr();
              md.update(text.getBytes("UTF-8"));
              byte[] digest = md.digest();
              String auth_token = null;
              StringBuffer hexString = new StringBuffer();
              
              for (int i = 0; i < digest.length; i++) {
                  String hex = Integer.toHexString(0xff & digest[i]);
                  if(hex.length() == 1) {
                    hexString.append('0');
                  }
                  hexString.append(hex);
              }
              
              auth_token = hexString.toString();
              
              /* If user had checked remember me option set cookie time to 2 months */
              if (remember_me != null) {
                
                /*Creating cookies for the users that login*/
                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge(60*60*24*60); // two months
                cookie.setPath("/DiscoV/");
                response.addCookie(cookie);
                
                // authentication cookie
                cookie = new Cookie("auth_cookie",auth_token);
                cookie.setMaxAge(60*60*24*60); // two months
                cookie.setPath("/DiscoV/");
                response.addCookie(cookie);
                
                
              }
              else {
                
                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge(-1); // browser session time
                cookie.setPath("/DiscoV/");
                response.addCookie(cookie); 
                
                // authentication cookie
                cookie = new Cookie("auth_cookie",auth_token);
                cookie.setMaxAge(-1); // two months
                cookie.setPath("/DiscoV/");
                response.addCookie(cookie);
                
                // Used to check if user has selected remember me
                session.setAttribute("remember_me",username);
              }
              
              // Search if there is a previous auth cookie in the DB
              String searchAuth = "SELECT * FROM cookies WHERE username = ?";
              prepStmt = con.prepareStatement(searchAuth);
              prepStmt.setString(1, username);
              result = prepStmt.executeQuery();
              
              // If there is an old auth cookie delete it from the DB
              if (result.next()) {
                searchAuth = "DELETE FROM cookies WHERE username =?";
                prepStmt = con.prepareStatement(searchAuth);
                prepStmt.setString(1, username);
                prepStmt.executeUpdate();
              }
              
              // Add auth cookie to DB
              String addUser = "INSERT INTO cookies VALUES (?,?);";
              prepStmt = con.prepareStatement(addUser);
              prepStmt.setString(1, username);
              prepStmt.setString(2, auth_token);
              prepStmt.executeUpdate();
              
              response.setContentType("text/html");
              response.sendRedirect("home.jsp");    
              
            }else{
                
               out.println("Username or/and password combination is incorrect. Please return to the login page and enter a correct one.");
            }  
         } catch (SQLException e) {
          out.print(e);
        } catch (NamingException e) {
          out.print(e);
        }
    }
  
}
