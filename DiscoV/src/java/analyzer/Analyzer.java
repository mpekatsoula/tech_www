/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.blinkenlights.jid3.*;
import org.blinkenlights.jid3.v1.*;
import org.blinkenlights.jid3.v2.*;

/**
* @author Pamitsis
* @author mpekatsoula
* @author AD
*/

public class Analyzer {
        public Analyzer(){
            
        }
     public  String[] mp3_tagger(String inputPath){
        // create an MP3File object representing our chosen file
        MediaFile oMediaFile = new MP3File(new File(inputPath));

        String info[] = new String[2];
        
        // any tags read from the file are returned, in an array, in an order which you should not assume
        ID3Tag[] aoID3Tag=null;
        try {
            aoID3Tag = oMediaFile.getTags();
        } catch (ID3Exception ex) {
            System.out.println(ex);
            Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        // let's loop through and see what we've got
        // (NOTE:  we could also use getID3V1Tag() or getID3V2Tag() methods, if we specifically want one or the other)
        for (int i=0; i < aoID3Tag.length; i++)
        {
            // check to see if we read a v1.0 tag, or a v2.3.0 tag (just for example..)
            if (aoID3Tag[i] instanceof ID3V1_0Tag)
            {
                ID3V1_0Tag oID3V1_0Tag = (ID3V1_0Tag)aoID3Tag[i];
                // does this tag happen to contain a title?
                if (oID3V1_0Tag.getTitle() != null)
                {
                    info[0] = oID3V1_0Tag.getTitle();
                    info[1] = oID3V1_0Tag.getArtist();
                }
                // etc.
            }
            else if (aoID3Tag[i] instanceof ID3V2_3_0Tag)
            {
                ID3V2_3_0Tag oID3V2_3_0Tag = (ID3V2_3_0Tag)aoID3Tag[i];
                // check if this v2.3.0 frame contains a title, using the actual frame name
                if (oID3V2_3_0Tag.getTIT2TextInformationFrame() != null)
                {
                    info[0] = oID3V2_3_0Tag.getTitle();
                    info[1] = oID3V2_3_0Tag.getArtist();
                }
                // but check using the convenience method if it has a year set (either way works)
            }
        }
        if ( info[0] == null)
          info[0] = "No Title";
        if ( info[1] == null)
          info[1] = "No Artist";
        return info;
     }
        
    /* Converts the mp3 file to .pcm */ 
    public  float mp3_to_pcm(String inputPath, String outputPath) throws IOException {

        Encoder encoder = new Encoder();
        
        File input = new File(inputPath);
        File output = new File(outputPath);
        
        AudioAttributes audio = new AudioAttributes();
        EncodingAttributes attrs = new EncodingAttributes();
        audio.setCodec("pcm_u8");
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);
        try {
            // encode the file
            encoder.encode(input,output,attrs);
            
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputFormatException ex) {
            Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncoderException ex) {
            Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  String command="/root/vamp-install/vamp-simple-host  qm-vamp-plugins:qm-tempotracker:tempo ";
	//command=command.concat(" " + outputPath);
        
        ProcessBuilder pb;
        pb = new ProcessBuilder("/root/vamp-install/vamp-simple-host", "qm-vamp-plugins:qm-tempotracker:tempo", outputPath);
        Process p=pb.start();
        
        try {
          p.waitFor();
        } 
        catch (InterruptedException e) {
            System.out.println("Error wait");
          Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, "process");
          
        }
        
        BufferedReader read = new BufferedReader(new InputStreamReader(p.getInputStream()));
        ArrayList <String> list=new ArrayList<String>();
        String s=null;
        while((s = read.readLine()) != null){
        	System.out.println(s);
        	list.add(s);
        }
        String delimeter=" ";
        int min=0,max=0;
        float [] beats;
        beats=new float[list.size()];
        System.out.println(list.size());
        for (int i=0;i<list.size();i++){
                System.out.println(list.get(i));
        	String temp[];
        	temp=list.get(i).split(delimeter);
        	list.set(i, temp[3]);
        	beats[i]=Float.parseFloat(list.get(i));
        }
        float sum=0;
        for (int i = 0; i < list.size(); i++){
        	if(beats[i]<beats[min])
        		min=i;
        	if(beats[i]>beats[max])
        		max=i;
        	sum+=beats[i];
       }
       float average=(sum-beats[min]-beats[max])/(list.size()-2);
       
       output.delete();
       
       return average;
    }

}