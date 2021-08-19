package view;


import entity.BloodBank;
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
import logic.LogicFactory;

/**
 *
 * @author harpa
 */
@WebServlet(name = "CreateBloodBank", urlPatterns = {"/CreateBloodBank"})
public class CreateBloodBank extends HttpServlet {

 private String errorMessage = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        response.setContentType( "text/html;charset=UTF-8" );
        try( PrintWriter out = response.getWriter() ) {
            /* TODO output your page here. You may use following sample code. */
            out.println( "<!DOCTYPE html>" );
            out.println( "<html>" );
            out.println( "<head>" );
            out.println( "<title>Create Blood Bank Record</title>" );
            out.println( "</head>" );
            out.println( "<body>" );
            out.println( "<div style=\"text-align: center;\">" );
            out.println( "<div style=\"display: inline-block; text-align: left;\">" );
            out.println( "<form method=\"post\">" );
            out.println( "Owner ID:<br>" );
            //instead of typing the name of column manualy use the static vraiable in logic
            //use the same name as column id of the table. will use this name to get date
            //from parameter map.
            out.printf( "<input type=\"number\" name=\"%s\" value=\"\"><br>", BloodBankLogic.OWNER_ID );
            out.println( "<br>" );
            out.println( "Name:<br>" );
            out.printf( "<input type=\"text\" name=\"%s\" value=\"\"><br>", BloodBankLogic.NAME);
            out.println( "<br>" );
            out.println( "Privately Owned:<br>" );
            out.printf( "<select name=\"%s\" value=\"\"><br>", BloodBankLogic.PRIVATELY_OWNED);
            out.println( "<option value=\"1\">Yes</option>" );
            out.println( "<option value=\"0\">No</option>" );
            out.println( "</select><br><br>" );
            out.println( "Establised Date Time:<br>" );
            
         //   out.printf( "<input type=\"datetime-local\" name=\"%s\" value=\"\"><br>",BloodBankLogic.ESTABLISHED );
            out.printf( "<input type=\"datetime-local\" name=\"%s\" step=1 value=\"\"><br>",BloodBankLogic.ESTABLISHED );
            out.println( "<br>" );
            
            out.println( "Employee Count:<br>" );
            out.printf( "<input type=\"number\" name=\"%s\" value=\"\"><br>", BloodBankLogic.EMPLOYEE_COUNT );
 
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
                errorMessage = null;
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
     * get method is called first when requesting a URL. since this servlet will create a host this method simple
     * delivers the html code. creation will be done in doPost method.
     *
     * @param request servlet request
     * @param response servlet response
     *
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
     * this method will handle the creation of entity. as it is called by user submitting data through browser.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        log( "POST" );
        BloodBankLogic aLogic = LogicFactory.getFor( "BloodBank" );
        String name = request.getParameter( BloodBankLogic.NAME );
        if( aLogic.getBloodBankWithName(name ) == null ){
            
            try {
                BloodBank bloodBank = aLogic.createEntity( request.getParameterMap() );
                //create person logic
                //use getwithid for person
                //set person to bloodbank using setter
          //??????? what if the person is not there should it return null... technically it should not create bloodbank    
         /*     PersonLogic personLogic= LogicFactory.getFor("Person");
                String string_ownerid=request.getParameter(BloodBankLogic.OWNER_ID);
                Integer ownerid= Integer.parseInt( string_ownerid);
                Person person=personLogic.getWithId(ownerid);
                bloodbank.setOwner
            */    
                
                aLogic.add( bloodBank );
            } catch( Exception ex ) {
                errorMessage = ex.getMessage();
            }
        } else {
            //if duplicate print the error message
            errorMessage = "Name: \"" + name + "\" already exists";
        }
        if( request.getParameter( "add" ) != null ){
            //if add button is pressed return the same page
            processRequest( request, response );
        } else if( request.getParameter( "view" ) != null ){
            //if view button is pressed redirect to the appropriate table
            response.sendRedirect( "BloodBankTable" );
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Create a BloodBank Entity";
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
