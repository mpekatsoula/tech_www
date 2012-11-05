/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
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
import myXML.XMLapps;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Spyrou Michalis
 */
public class Xmledit extends HttpServlet {

        /* This method write the data to the xml file  */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        
        String height = request.getParameter("height");
        String width = request.getParameter("width");
        String rotate = request.getParameter("rotate");
        String gray_bright = request.getParameter("gray_bright");
        String caption = request.getParameter("caption");
        
        String referrer;
        referrer = request.getHeader("referer");
        int IndexOf = referrer.indexOf("?loc="); 
        String path = referrer.substring(IndexOf+5,referrer.length());
            
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = null;
        Document doc = null;
        
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            doc = docBuilder.parse(path);
        } catch (SAXException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Node ImageXML = doc.getFirstChild();
        NodeList list = ImageXML.getChildNodes();
        
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            
            // get the height element, and update the value
            if ("height".equals(node.getNodeName()) && height!=null && !"".equals(height) ) {
                node.setTextContent(height);
	    }

            // get the width element, and update the value
            if ("width".equals(node.getNodeName()) && width!=null && !"".equals(width) ) {
                node.setTextContent(width);
	    }
            // get the rotate element, and update the value
            if ("rotate".equals(node.getNodeName()) && rotate!=null && !"".equals(rotate) ) {
                node.setTextContent(rotate);
	    }
            // get the gray_bright element, and update the value
            if ("gray_bright".equals(node.getNodeName()) && gray_bright!=null && !"".equals(gray_bright) ) {
                node.setTextContent(gray_bright);
	    }
            // get the caption element, and update the value
            if ("caption".equals(node.getNodeName()) && caption!=null && !"".equals(caption) ) {
                node.setTextContent(caption);
	    }

        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLapps.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         response.sendRedirect(request.getHeader("referer"));

    }
}
