package Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.FormValidationException;
import DAO.UserDAO;
import Entities.User;

@WebServlet("/registration")
public class Registration extends HttpServlet {

    public static final String VUE              = "/WEB-INF/Inscription.jsp";
    public static final String VUESucess        = "/WEB-INF/Connection.jsp";

    private String              resultat;
    private Map<String, String> erreurs          = new HashMap<String, String>();
    
  
    private UserDAO userDAO;
	 public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	        /* Affichage de la page d'inscription */
	        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmation = request.getParameter("confirmation");

		
		
		userDAO = new UserDAO();
		User user = new User();
		        try {
	            traiteEmail( email, user );
	            traitePassword( password, confirmation, user );
	           

	            if ( erreurs.isEmpty() ) {
	                resultat = "Succ�s de l'inscription.";

	            } else {
	                resultat = "�chec de l'inscription.";

	            }
	        } catch ( DAO.DAOException e ) {
	            resultat = "�chec de l'inscription : une erreur impr�vue est survenue, merci de r�essayer dans quelques instants.";
	            e.printStackTrace();
	        }
		        
                System.out.println(resultat);
                System.out.println(erreurs);

		        
		        if("Succ�s de l'inscription.".equals(resultat)) {
		        	user.setName("");
	        		userDAO.create(user);
		            this.getServletContext().getRequestDispatcher( VUESucess ).forward( request, response );
		        }
		        else {
	                request.setAttribute( "erreur", erreurs );
	                request.setAttribute( "resultat", resultat );

		        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	        
		        }
	}
	
    public void traiteEmail( String email, User user ) {
        try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( "email", e.getMessage() );
        }
        user.setEmail( email );
    }


    public void traitePassword( String password, String confirmation, User user ) {
        try {
            validationMotsDePasse( password, confirmation );
        } catch ( FormValidationException e ) {
            setErreur( "password", e.getMessage() );
            setErreur( "confirmation", null );
        }   

        user.setPassword( password );
    }

    public void validationEmail( String email ) throws FormValidationException {
        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new FormValidationException( "Bad Email's Format." );
            } 
            else if ( userDAO.find( email ) != null ) {
                throw new FormValidationException( "This email address is already in use." );
            }
        } else {
            throw new FormValidationException( "Thank you to enter an email address." );
        }
    }

    public void validationMotsDePasse( String motDePasse, String confirmation ) throws FormValidationException {
        if ( motDePasse != null && confirmation != null ) {
            if ( !(motDePasse.equals( confirmation )) ) {
                throw new FormValidationException( "Entered passwords are different." );
            } else if ( motDePasse.length() < 7 ) {
                throw new FormValidationException( "Too less password ." );
            }else if ( motDePasse.length() > 20 ) {
                throw new FormValidationException( "Too long password ." );
            }
        } else {
            throw new FormValidationException( "Thank you to enter and confirm your password." );
        }
    }



    public void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
	}
}
