/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Spyrou Michalis
 */
@WebServlet(name = "Upload", urlPatterns = {"/upload.do"})
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
        } 
        
        catch (FileUploadException ex) {
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
            int r = Math.abs(generator.nextInt());
            String reg = "[.*]";
            String replacingtext = "";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(itemName);
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                matcher.appendReplacement(buffer, replacingtext);
            }
            
            int IndexOf = itemName.indexOf("."); 
            String domainName = itemName.substring(IndexOf);

            String finalimage = buffer.toString()+"_"+r+domainName;
            
            /* Get the referer to see where to store the photo */
            String referrer;
            referrer = request.getHeader("referer");
            IndexOf = referrer.indexOf("index.jsp"); 
            int IndexAt = referrer.indexOf("8080");
            String _finalimage = "/usr/local/tomcat/webapps" + referrer.substring(IndexAt+4,IndexOf) + finalimage;
            File savedFile = new File(_finalimage);
            
            /* Create XML File */
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            try {
                docBuilder = docFactory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }
 
	    // root  image element
	    Document doc = docBuilder.newDocument();
	    Element rootElement = doc.createElement("image");
            doc.appendChild(rootElement);
            
            Element src = doc.createElement("src");
            src.appendChild(doc.createTextNode(referrer.substring(0,IndexOf)+finalimage));
            rootElement.appendChild(src);
            
            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode("Image"));
            rootElement.appendChild(title);   
             
            Element caption = doc.createElement("caption");
            caption.appendChild(doc.createTextNode("Caption"));
            rootElement.appendChild(caption); 
                 
            Element width = doc.createElement("width");
            width.appendChild(doc.createTextNode("200"));
            rootElement.appendChild(width); 
                   
            Element height = doc.createElement("height");
            height.appendChild(doc.createTextNode("200"));
            rootElement.appendChild(height);  
                      
            Element rotate = doc.createElement("rotate");
            rotate.appendChild(doc.createTextNode("0")); 
            rootElement.appendChild(rotate);
                        
            Element gray_bright = doc.createElement("gray_bright");
            gray_bright.appendChild(doc.createTextNode("0"));    
            rootElement.appendChild(gray_bright);
            
            // write the content into xml file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(new File(_finalimage + ".xml"));
            try {
  
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }
    
            /* Get user back to home */
            response.sendRedirect(referrer);
            try {
                item.write(savedFile);
            } 
            catch (Exception ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    }
}
}


