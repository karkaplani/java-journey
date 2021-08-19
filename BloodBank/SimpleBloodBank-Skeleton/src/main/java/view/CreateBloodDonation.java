package view;

import entity.BloodBank;
import entity.BloodDonation;
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
import logic.LogicFactory;

/**
 * This class is to display the view of the input page for the blood donation. 
 * The idea is the same as blood donation table view, but the do post method
 * includes more detail as it has to send data to the database.
 * 
 * @author Abdullah Ilgun
 */
@WebServlet(name = "CreateBloodDonation", urlPatterns = {"/CreateBloodDonation"})
public class CreateBloodDonation extends HttpServlet {
    
    private String errorMessage = "";
    
    /**
     * Creating the table using HTML code. 
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException  
     */
    protected void processRequest( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        try( PrintWriter out = response.getWriter() ) {
            
            
            out.println( "<!DOCTYPE html>" );
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>Create BloodDonation</title>" );
            out.println( "</head>" );
            out.println( "<body>" );
            out.println( "<div style=\"text-align: center;\">" );
            out.println( "<div style=\"display: inline-block; text-align: left;\">" );
            out.println( "<form method=\"post\">" );
            
            out.println( "Bank ID:<br>" );
            out.printf( "<input type=\"int\" name=\"%s\" value=\"\"><br>", BloodDonationLogic.BANK_ID );
            out.println( "<br>" );
            
            out.println( "Mililiters:<br>" );
            out.printf( "<input type=\"int\" name=\"%s\" value=\"\"><br>", BloodDonationLogic.MILILITERS );
            out.println( "<br>" );
            
            out.println( "Blood Group:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", BloodDonationLogic.BLOOD_GROUP );
            out.println( "<br>" );
            
            out.println( "RHD:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", BloodDonationLogic.FACTOR );
            out.println( "<br>" );
            
            out.println( "Created Time:<br>" );
            out.printf( "<input type=\"datetime-local\" name=\"%s\" value=\"\"><br>", BloodDonationLogic.CREATED );
            out.println( "<br>" );
            
            out.println( "<input type=\"submit\" name=\"view\" value=\"Add and View\">" );
            out.println( "<input type=\"submit\" name=\"add\" value=\"Add\">" );
            out.println( "</form>" );
            
            if( errorMessage != null && !errorMessage.isEmpty() ){
                out.println( "<p color=red>" );
                out.println( "<font color=red size=4px>" );
                out.println( errorMessage );
                out.println( "</font>" );
                out.println( "</p>" );
            }
            out.println( "<pre>" );
            out.println( "Submitted keys and values:" );
            out.println( toStringMap( request.getParameterMap() ) );
            out.println( "</pre>" );
            out.println( "</div>" );
            out.println( "</div>" );
            out.println( "</body>" );
            out.println( "</html>" );
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        log("GET");
        processRequest(request, response);
    }
      
    /**
     * Handles the HTTP <code>POST</code> method. Also includes a code to send the
     * user input to the database for the blood donation table view to extract and display.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log("POST");
        
        BloodDonationLogic bdLogic = LogicFactory.getFor( "BloodDonation" );  
        BloodBankLogic bankLogic = LogicFactory.getFor("BloodBank");
        
        try {
            BloodDonation bd = bdLogic.createEntity(request.getParameterMap());
            int bankID = Integer.parseInt(request.getParameterMap().get(BloodDonationLogic.BANK_ID)[0]);
            
            BloodBank bank = bankLogic.getWithId(bankID);
            
            bd.setBloodBank(bank);
            bdLogic.add(bd);
             
        } catch( NumberFormatException ex ) {
            errorMessage = ex.getMessage();
        }
        
          
        if( request.getParameter( "add" ) != null ){
            //if add button is pressed return the same page
            processRequest( request, response );
        } else if( request.getParameter( "view" ) != null ){
            //if view button is pressed redirect to the appropriate table
            response.sendRedirect( "BloodDonationTable" );
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Create a Blood Donation entity";
    }
    
    public static final boolean DEBUG = true;
    
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



