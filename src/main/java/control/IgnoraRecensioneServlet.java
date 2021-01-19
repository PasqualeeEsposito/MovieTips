package control;

import model.segnalazione.SegnalazioneDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class IgnoraRecensioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idRecensione = Integer.parseInt(request.getParameter("idRecensione"));
        SegnalazioneDAO serviceSegnalazione = new SegnalazioneDAO();
        serviceSegnalazione.doDeleteByIdRecensione(idRecensione);

        response.sendRedirect("./GestioneSegnalazioni");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
