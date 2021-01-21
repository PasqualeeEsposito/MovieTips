package control.gestioneFilm;

import control.MyServletException;
import model.film.Film;
import model.film.FilmDAO;
import model.recensione.Recensione;
import model.recensione.RecensioneDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet che gestisce la corretta visualizzazione della pagina di un film inserendo il film nella request
 */

@WebServlet(name = "FilmServlet", urlPatterns = "/Film")
public class FilmServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilmDAO filmDAO = new FilmDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        Film film = filmDAO.doRetrieveById(id);
        if (film == null) {
            throw new MyServletException("Siamo spiacenti, la pagina richiesta non è stata trovata");
        }
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        ArrayList<Recensione> recensioni = recensioneDAO.doRetrieveByIdFilm(id);
        request.setAttribute("film", film);
        request.setAttribute("recensioni", recensioni);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/film.jsp");
        requestDispatcher.forward(request, response);
    }
}