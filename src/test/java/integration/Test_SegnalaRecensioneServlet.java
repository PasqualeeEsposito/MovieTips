package integration;

import control.gestioneRecensione.SegnalaRecensioneServlet;
import model.gestioneUtente.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_SegnalaRecensioneServlet extends Mockito {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private SegnalaRecensioneServlet servlet;

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        servlet = new SegnalaRecensioneServlet();
    }

    @Test
    public void testSegnalaRecensione1() throws ServletException, IOException {
        request.addParameter("idRecensione", "1");
        request.addParameter("idFilm", "1");
        request.getSession().setAttribute("utente", null);
        String message = "Errore: accesso non effettuato";
        servlet.doGet(request, response);
        String result = (String) request.getAttribute("errorTest");
        assertEquals(message, result);
    }

    @Test
    public void testSegnalaRecensione2() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setUsername("roberta_esposito");
        utente.setRuolo("010000");
        request.addParameter("idRecensione", "1");
        request.addParameter("idFilm", "1");
        request.getSession().setAttribute("utente", utente);
        String message = "Errore: e-mail utente non convalidata";
        servlet.doGet(request, response);
        String result = (String) request.getAttribute("errorTest");
        assertEquals(message, result);
    }

    @Test
    public void testSegnalaRecensione3() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setUsername("fabrizio_ceriello");
        utente.setRuolo("001000");
        request.addParameter("idRecensione", "1");
        request.addParameter("idFilm", "1");
        request.getSession().setAttribute("utente", utente);
        String message = "Ok: recensione segnalata";
        servlet.doGet(request, response);
        String result = (String) request.getAttribute("errorTest");
        assertEquals(message, result);
    }
}
