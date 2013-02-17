package discov.database;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class Register extends HttpServlet {
     
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    
    // Validate reCaptcha
    String remoteAddr = request.getRemoteAddr();
    ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
    
    reCaptcha.setPrivateKey("6Lds39oSAAAAABy4hew8M5c70gY9kIKNxtjC6C1T");
    
    ReCaptchaResponse reCaptchaResponse;
    reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr,request.getParameter("recaptcha_challenge_field"), request.getParameter("recaptcha_response_field"));

    // if reCapcha is not valid error and exit
    if (!reCaptchaResponse.isValid()) {
       out.println("Error Captcha");
       return;
    }

   
      try {
        Context envContext = new InitialContext();
            
        DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/m_users");
        Connection con = ds.getConnection();
 
        /* Get form info */
        String username = request.getParameter("username");
        String password = request.getParameter("pass");
        String email = request.getParameter("email");
            
        /* Check if users exists. SQLi secure */
        String SearchUserQuery = "SELECT username FROM users WHERE username = ?";
        PreparedStatement prepStmt = con.prepareStatement(SearchUserQuery);
        prepStmt.setString(1, username);
        ResultSet rs = prepStmt.executeQuery();
        
        if ( rs.next() ){
                
                // send to error page
                 
            }
            else {
                
                /* Add user to the database */
                String AddUserQuery = "INSERT INTO users VALUES (?, SHA1(?), ? )" ;
                prepStmt = con.prepareStatement(AddUserQuery);
                prepStmt.setString(1, username);
                prepStmt.setString(2, password);
                prepStmt.setString(3, email);
                prepStmt.executeUpdate();
                
                String location = "http://m1nato.no-ip.org:8080/DiscoV/home.jsp";
                response.setContentType("text/html");
                response.sendRedirect(location);
                
            }
        }  catch (SQLException e) {
          out.print(e);
        } catch (NamingException e) {
          out.print(e);
        }
    }
}
