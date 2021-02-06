package control.gestioneUtente;

import control.MyServletException;
import model.gestioneUtente.Utente;
import model.gestioneUtente.UtenteDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet che gestisce il login inserendo l'utente nella sessione. Lancia l'eccezione MyServletException se i dati inseriti dall'utente non sono validi
 */
@WebServlet(name = "AccessoServlet", urlPatterns = "/Accesso")
public class AccessoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UtenteDAO utenteDAO = new UtenteDAO();

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if (utente != null) {
            throw new MyServletException("Operazione non autorizzata");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/login.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws MyServletException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, MyServletException {
        request.setCharacterEncoding("UTF-8");
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if (utente != null) {
            throw new MyServletException("Operazione non autorizzata");
        }
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");
        utente = new Utente();
        String errore = "";
        switch (utenteDAO.signIn(mail, password, utente)) {
            case -1:
                errore = "Errore: lunghezza e-mail";
                request.setAttribute("errorTest", errore);
                break;
            case -2:
                errore = "Errore: formato e-mail";
                request.setAttribute("errorTest", errore);
                break;
            case -3:
                errore = "Errore: e-mail non esistente";
                request.setAttribute("errorTest", errore);
                break;
            case -4:
                errore = "Errore: e-mail bannata";
                request.setAttribute("errorTest", errore);
                //throw new MyServletException("Utente bannato");
                break;
            case -5:
                errore = "Errore: lunghezza password";
                request.setAttribute("errorTest", errore);
                break;
            case -6:
                errore = "Errore: formato password";
                request.setAttribute("errorTest", errore);
                break;
            case -7:
                errore = "Errore: password non corrispondente all’username";
                request.setAttribute("errorTest", errore);
                break;
            default:
                errore = "Ok: accesso effettuato";
                request.setAttribute("errorTest", errore);
                request.getSession().setAttribute("utente", utente);
                response.sendRedirect(".");
                return;
        }
        throw new MyServletException(errore);
        //throw new MyServletException("Username e/o password non validi");
    }
}
