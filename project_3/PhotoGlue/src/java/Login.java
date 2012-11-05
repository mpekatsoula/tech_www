/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Spyrou Michalis
 */
@WebServlet(name = "Login", urlPatterns = {"/login.do"})
 public class Login extends HttpServlet {
    
    /**
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Servlet JDBC</title></head>");
        out.println("<body>");
        out.println("<h1>Servlet JDBC</h1>");
        out.println("</body></html>");  
        
        
        // connecting to database
        Connection con = null;  
        Statement stmt = null;
        ResultSet rs = null;
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            /*Connect to the database */
            con =DriverManager.getConnection ("jdbc:mysql://localhost:3306/photoglue_users","root","I WILL NOT GIVE YOU MY PASSWORD :)");
            stmt = con.createStatement();
            
            String username = request.getParameter("username");
            String password = request.getParameter("pass");
            
            /* Search of user in database */
            String SearchUserQuery = "SELECT username FROM users WHERE username = \"" + username + "\" AND password = SHA1('" + password + "')" ;
            rs = stmt.executeQuery(SearchUserQuery);
            if ( rs.next() ){
                
                String dirname;
                    
                MessageDigest md = MessageDigest.getInstance( "SHA1" );
                md.update( password.getBytes() );
                MessageDigest md2 = MessageDigest.getInstance( "SHA1" );
                md2.update( username.getBytes() );
                    
                /* Only the first 4 + 4 chars of sha1 of the password as the file name */
                String part1 = (new BigInteger( 1, md.digest() ).toString(16)).substring(0,4);
                String part2 = (new BigInteger( 1, md2.digest() ).toString(16)).substring(4,8);
                dirname = "/PhotoGlue/photos/" + part1 + part2 + "/index.jsp";
                 
                response.setContentType("text/html");
                response.sendRedirect(dirname);
            }
            else {
                
                /* Add user to the database */
                out.println("Username or password incorect. Please try again.");
                
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException e) {
            
            throw new ServletException("Servlet Could not display records.", e);
        } 
        catch (ClassNotFoundException e) {
            throw new ServletException("JDBC Driver not found.", e);
        } 
        finally {
            try {
                
                if(rs != null) {
                    rs.close();
                    rs = null;
                }
                
                if(stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                
                if(con != null) {
                    con.close();
                    con = null;
                }
            } 
            catch (SQLException e) {
            }
        }
        
        out.close();
    }
  
  }
