/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author mpekatsoula
 */
public class SimilarSongFinder {
  
  public ArrayList Search(String artist, String track) {
    
    ArrayList similars = new ArrayList();
    
    try {
         
      Context envContext = new InitialContext();

      DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/m_users");
      Connection con = ds.getConnection();
         
      String findSong = "SELECT * FROM songs WHERE artist = ? AND song = ?";
      PreparedStatement prepStmt = con.prepareStatement(findSong);
      prepStmt.setString(1, artist);
      prepStmt.setString(2, track);
      ResultSet result = prepStmt.executeQuery();
              
      float bpm = (float) 0;
      if (result.next()) {

        /* Get bpm */
        bpm = Float.parseFloat(result.getString(3));
        System.out.println("bpm:" + bpm);
                
      }
      
      if ( bpm > 0) {

        /* Search similar bpms */
        String findSimilar = "SELECT * FROM songs";
        prepStmt = con.prepareStatement(findSimilar);
        result = prepStmt.executeQuery();


        float temp_bpm;

        while (result.next()) {

          temp_bpm = Float.parseFloat(result.getString(3));

          if ( Math.abs(temp_bpm - bpm) < 15 ) {  // You found a similar!!!
            similars.add(result.getString(1));
            similars.add(result.getString(2));
          }
        }
      }
      else {
        
      }
   }  catch (SQLException e) {
      System.out.print(e);
   } catch (NamingException e) {
      System.out.print(e);
   }
    
    return similars;
  }
  
}
