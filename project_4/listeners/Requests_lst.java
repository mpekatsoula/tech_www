
package listeners;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Spyrou Michalis
 */
public class Requests_lst implements ServletRequestListener {
    
    
      private static long total_edits = 0;
      private static long total_uploads = 0;
      private static int TotalDayEdits[] = new int[25];
      private static int TotalDayUploads[] = new int[25];
      private static int Day = -1;
      private static int last_hour = -1;

      
    @Override
  public void requestInitialized(ServletRequestEvent sre) {

    ServletContext context = sre.getServletContext();
    ServletRequest request = sre.getServletRequest();

   
      //The static class variable reqCount is incremented in this block;
      //the incrementing of the variable is synchronized so that one
      // thread is not reading the variable while another increments it

    synchronized (context){
        
       int temp_hour, temp_day;
        
        /* Get current date */
        Date date = new Date();
        
        /* Create a calendar */
        Calendar calendar = GregorianCalendar.getInstance();
        
        /* assign calendar to given date */
        calendar.setTime(date); 
        
        /* Get 24h format */
        temp_hour = calendar.get(Calendar.HOUR_OF_DAY);
        
        /* Get current Day */
        temp_day = calendar.get(Calendar.DAY_OF_MONTH);
        
        /* Initialize variables */
        if (Day == -1 ){
            
            last_hour = temp_hour;
            Day = temp_day; 
            for (int i = 1; i < 25 ; i++){
                TotalDayEdits[i] = 0;
                TotalDayUploads[i] = 0;
            }
        }
        
        if ( (((HttpServletRequest)request).getRequestURI()).equals("/PhotoGlue/xmledit.do") ) {
            
            total_edits++;

            /* If the day has changed empty buffer */
            if ( Day == temp_day ) {
                TotalDayEdits[temp_hour]++;
            }
            else {
                for(int i = 0; i < 24; i ++){
                    TotalDayEdits[i] = 0;
                }
            }

            Day = temp_day;

        }
        
        if ( (((HttpServletRequest)request).getRequestURI()).equals("/PhotoGlue/upload.do") ) {
            total_uploads++;
                  /* If the day has changed empty buffer */
            if ( Day == temp_day ) {
                TotalDayUploads[temp_hour]++;
            }
            else {
                for(int i = 0; i < 24; i ++){
                    TotalDayUploads[i] = 0;
                }
            }

            Day = temp_day;
        }
        
        
    }//synchronized

  }

    @Override
  public void requestDestroyed(ServletRequestEvent sre) {

  }
  
    public static long getEditCount() {
        
        return total_edits;
        
    }
      
    public static long getUploadCount() {
        
        return total_uploads;
        
    }
    
    
    /**
     *
     */
    synchronized public void WriteRequestsData( ){

        try{
 
            /* Write Total Edits data */
            FileWriter fstream = new FileWriter("/usr/local/tomcat/webapps/PhotoGlue/data2.json");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("{\n   \"label\": \"Daily Image Edits (24h)\",");
            String data = "";
            for (int i = 0; i < 24 ; i++){
                if ( i == 23){
                
                data += "[" + (i+1) + "," + TotalDayEdits[0] + "]";
                }
                    
                else {
                data += "[" + (i+1) + "," + TotalDayEdits[i+1] + "],";
                }
                
            }
            out.write("\n\"data\":[" + data + "]\n}");
            out.close();
        }
        catch (Exception e){
            
            System.err.println("Error: " + e.getMessage());
            
        }
        
         try{
 
            /* Write Total Uploads data */
            FileWriter fstream = new FileWriter("/usr/local/tomcat/webapps/PhotoGlue/data3.json");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("{\n   \"label\": \"Daily Image Uploads (24h)\",");
            String data = "";
            for (int i = 0; i < 24 ; i++){
  
               if ( i == 23){
                
                    data += "[" + (i+1) + "," + TotalDayUploads[i+1] + "]";
                }
                    
                else {
                    data += "[" + (i+1) + "," + TotalDayUploads[i+1] + "],";
                }
                
            }
            out.write("\n\"data\":[" + data + "]\n}");
            out.close();
        }
        catch (Exception e){
            
            System.err.println("Error: " + e.getMessage());
            
        }
    }
}
