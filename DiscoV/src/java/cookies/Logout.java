/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookies;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author mpekatsoula
 */
public class Logout extends HttpServlet {

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
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
   
    Cookie cookie = null;
    Cookie[] cookies = ((HttpServletRequest) request).getCookies();

    String username = null;
    
     for(int i=0; i < cookies.length; i++) {
       cookie = cookies[i];
       if ("username".equals(cookie.getName())) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            username = cookie.getValue();
         }
        if ("auth_cookie".equals(cookie.getName())) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
         }
     }
         PrintWriter out = response.getWriter();   
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
      out.println(e);
     } catch (NamingException e) {out.println(e);
     }
     
     response.sendRedirect("index.html");
     
    }
  }
