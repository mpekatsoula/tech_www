/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
@WebServlet(name = "Register", urlPatterns = {"/register.do"})
 public class Register extends HttpServlet {
    
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
            String email = request.getParameter("email");
            
            /* First we need to check if user exists */
            String SearchUserQuery = "SELECT username FROM users WHERE username = \"" + username + "\"";
            rs = stmt.executeQuery(SearchUserQuery);
            if ( rs.next() ){
                
                 out.println("Username " + rs.getString("username") + " already exists. Please go back and choose a new.");  
                 
            }
            else {
                
                /* Add user to the database */
                String AddUserQuery = "INSERT INTO users VALUES ('" + username + "', SHA1('" + password + "'), '" + email + "' )" ;
                stmt.executeUpdate(AddUserQuery);
                
                
                /* Create the user's folder for storing images.
                 * For each user we have a pseudo-random folder 
                 * beacuse we are not allowed to use cookies.    */
                String dirname;
                try {
                    
                    MessageDigest md = MessageDigest.getInstance( "SHA1" );
                    md.update( password.getBytes() );
                    MessageDigest md2 = MessageDigest.getInstance( "SHA1" );
                    md2.update( username.getBytes() );
                    
                    /* Only the first 4 + 4 chars of sha1 of the password as the file name */
                    String part1 = (new BigInteger( 1, md.digest() ).toString(16)).substring(0,4);
                    String part2 = (new BigInteger( 1, md2.digest() ).toString(16)).substring(4,8);
                    dirname = "/usr/local/tomcat/webapps/PhotoGlue/photos/" + part1 + part2 ;
                    new File(dirname).mkdirs();
                    
                    /* Copy default index.jsp and Upload.html files for the new user into his folder */
                    String orig ="/usr/local/tomcat/webapps/PhotoGlue/default_user_index.jsp";
                    String dest = dirname + "/index.jsp";
                    InputStream in_str = new FileInputStream(orig);
                    OutputStream out_str = new FileOutputStream(dest);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in_str.read(buf)) > 0) {
                        out_str.write(buf, 0, len);
                    }
                    
                    in_str.close();
                    out_str.close(); 
                    
                    dest = "/PhotoGlue/photos/" + part1 + part2 + "/index.jsp";
                    response.setContentType("text/html");
                    response.sendRedirect(dest);

                } catch (NoSuchAlgorithmException ex) {
                    
                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                

                
            }

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
