package listeners;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class Sessions_lst implements HttpSessionListener {

private static int activeSessions = 0;
private static int TotalSessions = 0;
private static int TotalDaySessions[] = new int[25];
private static int Day = -1;;
    /**
     *
     * @param se
     */
    @Override
    synchronized public void sessionCreated(HttpSessionEvent se) {
                
        activeSessions++;
        TotalSessions ++;
        
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
            
            Day = temp_day; 
            for (int i = 1; i < 25 ; i++){
                TotalDaySessions[i] = 0;
            }
        }
        
        /* If the day has changed empty buffer */
        if ( Day == temp_day ) {
            TotalDaySessions[temp_hour]++;
        }
        else {
            for(int i = 0; i < 24; i ++){
                TotalDaySessions[i] = 0;
            }
        }
        
        Day = temp_day;
        

    }

    /**
     *
     * @param se
     */
    @Override
    synchronized public void sessionDestroyed(HttpSessionEvent se) {

        if(activeSessions > 0) {
            activeSessions--;
        }
    }

    public static int getActiveSessions() {
        
        return activeSessions;
        
    }
    
    public static int getTotalSessions() {
        
        return TotalSessions;
        
    }
    
    /* Writes the output to the file specified */
    synchronized public void WriteSessionsData( String datapath ){
    
        try{
 
            FileWriter fstream = new FileWriter(datapath);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("{\n   \"label\": \"Daily Sessions (24h)\",");
            String data = "";
            for (int i = 0; i < 24 ; i++){
                if ( i == 23 ){
                    data += "[" + (i+1) + "," + TotalDaySessions[0] + "]";
                }
                else {
                    data += "[" + (i+1) + "," + TotalDaySessions[i+1] + "],";
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
