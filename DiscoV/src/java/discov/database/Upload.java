/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package discov.database;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import analyzer.Analyzer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import lastfm.Lastfm_func;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "Upload", urlPatterns = {"/Upload.do"})
 public class Upload extends HttpServlet {
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
  
    
    PrintWriter out = response.getWriter();
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

    if (!isMultipart) {
      out.println("File Not Uploaded");
    } 
    else {

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;   
      try {
        items = upload.parseRequest(request);
      } catch (FileUploadException ex) {
        Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
      }
      Iterator itr = items.iterator();

        while (itr.hasNext()) {

        FileItem item = (FileItem) itr.next();

        if (item.isFormField()){
            
            String name = item.getFieldName();
            String value = item.getString();
            
        } 
        else {
      
            String itemName = item.getName();
            Random generator = new Random();
               
           
            String file = "/tmp/" + itemName;
            File savedFile = new File(file);

            try {
                item.write(savedFile);
            } 
            catch (Exception ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }

        Analyzer file_analyze = new Analyzer();
        String[] info = file_analyze.mp3_tagger(file);
        
        /* Take and store bpm to DB */
        float bpm = file_analyze.mp3_to_pcm(file,file + ".wav");
        String db_bpm = Float.toString(bpm);
        
        try {
          Context envContext = new InitialContext();

          DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/m_users");
          Connection con = ds.getConnection();

          /* Store to songs database */
          String AddSongQuery = "INSERT INTO songs VALUES (?, ?, ?, ?)" ;
          PreparedStatement prepStmt = con.prepareStatement(AddSongQuery);
          prepStmt.setString(1, info[1]);
          prepStmt.setString(2, info[0]);
          prepStmt.setString(3, db_bpm);
          
          /* Search genre to lastfm */
          Lastfm_func get_genre = new Lastfm_func();
          Collection temp = null;
          //temp = get_genre.GetGenre(info[0],info[1]);
          String argument4;
          argument4 = "none";     
          prepStmt.setString(4, argument4);
             
          prepStmt.executeUpdate();

          /* Store to latest searches DB */
          
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
          System.out.println(username + " - " + info[0] + " - " + info[1]);
          AddSongQuery = "INSERT INTO searches VALUES (?, ?, ?)";
          prepStmt = con.prepareStatement(AddSongQuery);
          prepStmt.setString(1, username);
          prepStmt.setString(2, info[1]);
          prepStmt.setString(3, info[0]);
          prepStmt.executeUpdate();
          
          savedFile.delete();
          
        }  catch (SQLException e) {
          out.print(e);
        } catch (NamingException e) {
          out.print(e);
        }
        
        /* Go back to home */
        response.sendRedirect("home.jsp");

  }
}
    }
  }
}