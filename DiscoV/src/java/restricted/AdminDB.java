/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restricted;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.sql.DataSource;
import org.apache.catalina.User;


public class AdminDB extends HttpServlet {

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
        
        try {
                    
            Context envContext = new InitialContext();
                                    
            DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/m_users");
            Connection con = ds.getConnection();
            
            String username = request.getParameter("username");
            
            String findUser = "DELETE from users WHERE username = ?";
            PreparedStatement prepStmt = con.prepareStatement(findUser);
            prepStmt.setString(1, username);
            prepStmt.executeUpdate();
            
            response.sendRedirect("adminpage.jsp");  
            
        } catch (SQLException e) {
          out.print(e);
        } catch (NamingException e) {
          out.print(e);
        }
    }
    
    public ArrayList DisplayUsers(){

            ArrayList users = new ArrayList();
       
          Context envContext = null;
        try {
          envContext = new InitialContext();
        } catch (NamingException ex) {
          Logger.getLogger(AdminDB.class.getName()).log(Level.SEVERE, null, ex);
        }

          DataSource ds = null;
        try {
          ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/m_users");
        } catch (NamingException ex) {
          Logger.getLogger(AdminDB.class.getName()).log(Level.SEVERE, null, ex);
        }
          Connection con = null;
        try {
          con = ds.getConnection();
        } catch (SQLException ex) {
          Logger.getLogger(AdminDB.class.getName()).log(Level.SEVERE, null, ex);
        }
          String findUser = "SELECT * FROM users";
          PreparedStatement prepStmt = null;
        try {
          prepStmt = con.prepareStatement(findUser);
        } catch (SQLException ex) {
          Logger.getLogger(AdminDB.class.getName()).log(Level.SEVERE, null, ex);
        }
          ResultSet result = null;
        try {
          result = prepStmt.executeQuery();
        } catch (SQLException ex) {
          Logger.getLogger(AdminDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
          while (result.next()) {
            users.add(result.getString(1));
          }

        } catch (SQLException ex) {
          Logger.getLogger(AdminDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
        }
      
}
