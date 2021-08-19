package view;

import entity.BloodBank;
import entity.BloodDonation;
import entity.DonationRecord;
import entity.Person;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.BloodBankLogic;
import logic.BloodDonationLogic;
import logic.DonationRecordLogic;
import logic.LogicFactory;
import logic.PersonLogic;

/**
 * Servlet class for the blood donation form. 
 */
@WebServlet( name = "DonateBloodForm", urlPatterns = { "/DonateBloodForm" } )
public class DonateBloodForm extends HttpServlet {
    String errorMessage = "";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        try( PrintWriter out = response.getWriter() ) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<title>Donate Blood Form</title>");
            out.println("<link rel=\"stylesheet\" href=\"style/bloodform.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div style=\"display: flex; text-align: left;flex-direction: column; margin: 0 50px\">");
            out.println("<form action=\"DonateBloodForm\" method=\"post\">");
            out.println("<div>");
            out.println("<h1>Person</h1>"); //? 
            out.println("<fieldset>");
            out.println("<label for=\"firstname\">First name</label>");
            out.println("<input type=\"text\" name=\"firstname\" value=\"Shawn\"/>");
            out.println("<label for=\"secondname\">Last name</label>");
            out.println("<input type=\"text\" name=\"secondname\" value=\"Emmami\"/>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<label for=\"phonenumber\">Phone</label>");
            out.println("<input type=\"tel\" name=\"phonenumber\"/>");
            out.println("<label for=\"address\">Address</label>");
            out.println("<input type=\"text\" name=\"address\"/>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<label for=\"birthday\">Date of Birth</label>");
            out.println("<input type=\"date\" name=\"birthday\">");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("<br/>");
            out.println("<div>");
            
            out.println("<h1>Blood</h1>");
            out.println("<fieldset>");
            out.println("<label for=\"bloodgroup\">Blood Group</label>");
            out.println("<input type=\"text\" name=\"bloodgroup\">");
            out.println("<label for=\"factor\">RHD</label>");
            out.println("<input type=\"text\" name=\"factor\">");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<label for=\"amount\">Amount</label>");
            out.println("<input type=\"text\" name=\"amount\">");
            out.println("<label for=\"${DonationRecordLogic.TESTED}\" >Tested</label>");
            out.println("<select name=\"${DonationRecordLogic.TESTED}\">");
            out.println("<option>Positive</option>");
            out.println("<option>Negative</option>");
            out.println("</select>");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("<br />");
            
            out.println("<div>");
            out.println("<h1>Administration</h1>");
            out.println("<fieldset>");
            out.println("<label for=\"hospital\">Hospital</label>");
            out.println("<input type=\"text\" name=\"hospital\">");
            out.println("<label for=\"administrator\">Administrator</label>");
            out.println("<input type=\"text\" name=\"administrator\">");
            out.println("</fieldset>");
            out.println("<fieldset class=\"field\">");
            out.println("<label for=\"date\">Date</label>");
            out.println("<input type=\"date\" name=\"date\">");
            out.println("<label for=\"bloodbank\">Blood Bank</label>");
            out.println("<input type=\"text\" name=\"bloodbank\">");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("<br/>");
            out.println("<input type=\"submit\" name=\"submit\" value=\"Submit\">");
            out.println("<input type=\"submit\" name=\"view\" value=\"Submit and View\">");
            out.println("</form>");
            out.println("<pre>Submitted keys and values:</pre>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
        }
    }

    private String toStringMap( Map<String, String[]> values ) {
        StringBuilder builder = new StringBuilder();
        values.forEach( ( k, v ) -> builder.append( "Key=" ).append( k )
                .append( ", " )
                .append( "Value/s=" ).append( Arrays.toString( v ) )
                .append( System.lineSeparator() ) );
        return builder.toString();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        log( "GET" );
        processRequest( request, response );
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        log( "POST" );
        
        BloodDonationLogic bdLogic = LogicFactory.getFor( "BloodDonation" );  
        BloodBankLogic bankLogic = LogicFactory.getFor("BloodBank");
        PersonLogic personLogic = LogicFactory.getFor("Person");
        DonationRecordLogic drLogic = LogicFactory.getFor("DonationRecord");
        
        //Adding blood donation
        try {
            BloodDonation bd = bdLogic.createEntity(request.getParameterMap());
            int bankID = Integer.parseInt(request.getParameterMap().get(BloodDonationLogic.BANK_ID)[0]);
            
            BloodBank bank = bankLogic.getWithId(bankID);
            
            bd.setBloodBank(bank);
            bdLogic.add(bd);
             
        } catch( NumberFormatException ex ) {
            errorMessage = ex.getMessage();
        }
        
        //Adding person
        Person person = personLogic.createEntity( request.getParameterMap() );
                personLogic.add( person );
                
        if( request.getParameter( "add" ) != null ){
            //if add button is pressed return the same page
            processRequest( request, response );
        } else if( request.getParameter( "view" ) != null ){
            //if view button is pressed redirect to the appropriate table
            response.sendRedirect( "PersonTable" );
        }
        
        Map<String, String[]> map = request.getParameterMap();
        if( map.containsKey( "view" ) ){
            response.sendRedirect( "UsernameTableViewNormal" );
        } else if( map.containsKey( "submit" ) ){
            processRequest( request, response );
        }
        
        //Adding donation record
        try {
            DonationRecord donrec = drLogic.createEntity( request.getParameterMap() );
            drLogic.add( donrec );
        } catch( Exception ex ) {
            errorMessage = ex.getMessage();
        }
        if( request.getParameter( "add" ) != null ){
         //if add button is pressed return the same page
            processRequest( request, response );
        } else if( request.getParameter( "view" ) != null ){

              response.sendRedirect( "DonationRecordTable" );
          }  
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Sample page of multiple Eelements";
    }

    private static final boolean DEBUG = true;

    public void log( String msg ) {
        if( DEBUG ){
            String message = String.format( "[%s] %s", getClass().getSimpleName(), msg );
            getServletContext().log( message );
        }
    }

    public void log( String msg, Throwable t ) {
        String message = String.format( "[%s] %s", getClass().getSimpleName(), msg );
        getServletContext().log( message, t );
    }
}
