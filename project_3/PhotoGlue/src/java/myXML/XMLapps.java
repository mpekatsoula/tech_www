package myXML;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Spyrou Michalis
 */
public class XMLapps {

    public String Caption(String path){
        
        
        File file = new File(path);  
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document doc = null;
        try {
            doc = (Document) builder.parse(file);
        } catch (SAXException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList node;
                node = doc.getElementsByTagName("image");
        Element element = (Element) node.item(0);
        
        String ImageCaption;
        NodeList title = element.getElementsByTagName("caption");
        Element line = (Element) title.item(0);
        ImageCaption = getCharacterDataFromElement(line);
        
        String LabelTag;
        
        LabelTag = "<label>" + ImageCaption + "</label>";
                
       return LabelTag;
    }
    public String DisplayImage(String path) {
        
        File file = new File(path);  
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document doc = null;
        try {
            doc = (Document) builder.parse(file);
        } catch (SAXException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList node;
        
        String ImageLocation, ImageTitle, ImageWidth, ImageHeight, ImageRotate, ImageGray;
        node = doc.getElementsByTagName("image");
        Element element = (Element) node.item(0);
          
        NodeList title = element.getElementsByTagName("src");
        Element line = (Element) title.item(0);
        ImageLocation = getCharacterDataFromElement(line);
        
        title = element.getElementsByTagName("title");
        line = (Element) title.item(0);
        ImageTitle = getCharacterDataFromElement(line);
        
        title = element.getElementsByTagName("width");
        line = (Element) title.item(0);
        ImageWidth = getCharacterDataFromElement(line);
        
        title = element.getElementsByTagName("height");
        line = (Element) title.item(0);
        ImageHeight = getCharacterDataFromElement(line);
        
        title = element.getElementsByTagName("rotate");
        line = (Element) title.item(0);
        ImageRotate = getCharacterDataFromElement(line);
        
        title = element.getElementsByTagName("gray_bright");
        line = (Element) title.item(0);
        ImageGray = getCharacterDataFromElement(line);
        
        String ImageTag;
        
        ImageTag = "<img src=\"" + ImageLocation + "\" name=\"" + ImageTitle + "\"  width=\"" +ImageWidth + "\" height=\"" + ImageHeight + "\" style=\"-moz-transform:rotate(" + ImageRotate + "deg); -webkit-filter: grayscale("+ImageGray+");\" />";
        
        return ImageTag;
        
    }
    
    
    
   /* Scans .xml files and parses the info. Then it creates the Image tag. */
   public String[] ScanXMLFiles(String path)  {
        
       
       
       File file = new File(path);  
       File[] Files = file.listFiles(new RegexFilter());
       
       DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        }
       Document doc = null;

        String ImageCaption;
        String ImageLocation;
        String ImageTitle;
        String[] ImageTags = new String[Files.length];
        NodeList node;
        for (int i = 0; i < Files.length; i++) {
            try {
                doc = (Document) builder.parse(Files[i]);
            } catch (SAXException ex) {
                Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            node = doc.getElementsByTagName("image");
            Element element = (Element) node.item(0);
            
            NodeList title = element.getElementsByTagName("caption");
            Element line = (Element) title.item(0);
            ImageCaption = getCharacterDataFromElement(line);
            title = element.getElementsByTagName("src");
            line = (Element) title.item(0);
            ImageLocation = getCharacterDataFromElement(line);
            title = element.getElementsByTagName("title");
            line = (Element) title.item(0);
            ImageTitle = getCharacterDataFromElement(line);
            ImageTags[i] = "<a href=\"http://m1nato.no-ip.org:8080/PhotoGlue/edit.jsp?loc="+Files[i]+"\"><img src=\"" + ImageLocation + "\" name=\"" + ImageTitle + "\"  width=\"150\" height=\"150\" /></a>";
            
        
        }
        
       
        
        return ImageTags;
   
    }
   
    class RegexFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {

            if (pathname.getName().endsWith(".xml")) {
                return true;
            }

            return false;
        }
    }
    
    public String CreateImageTags() {
        return null;
        
        
    }
    
  public static String getCharacterDataFromElement(Element e) {
    Node child = e.getFirstChild();
    if (child instanceof CharacterData) {
      CharacterData cd = (CharacterData) child;
      return cd.getData();
    }
    return "";
  }
  
}
