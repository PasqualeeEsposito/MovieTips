package integration;

import control.gestioneUtente.BannaUtenteServlet;
import model.gestioneUtente.Utente;
import model.gestioneUtente.UtenteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_BannaUtenteServlet {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private BannaUtenteServlet servlet;
    private HttpSession session;
    private UtenteDAO utenteDAO = new UtenteDAO();

    @BeforeEach
    public void setUp() {
        session = Mockito.mock(HttpSession.class);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        servlet = new BannaUtenteServlet();
    }

    @Test
    public void testBanUtente1() throws ServletException, IOException {
        request.addParameter("username", "roberta_esposito");
        String message = "AE_FAIL";
        servlet.doGet(request, response);
        String result = (String) request.getAttribute("errorTest");
        assertEquals(message, result);
    }

    @Test
    public void testBanUtente2() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setRuolo("001000");
        Mockito.when(request.getSession().getAttribute("utente")).thenReturn(utente);
        request.addParameter("username", "roberta_esposito");
        String message = "RM_FAIL";
        servlet.doGet(request, response);
        String result = (String) request.getAttribute("errorTest");
        assertEquals(message, result);
    }

    @Test
    public void testBanUtente3() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setUsername("marco_bellamico");
        utente.setRuolo("000001");
        request.addParameter("username", "marco_bellamico");
        String message = "CR_FAIL";
        servlet.doGet(request, response);
        String result = (String) request.getAttribute("errorTest");
        assertEquals(message, result);
    }

    @Test
    public void testBanUtente4() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setUsername("marco_bellamico");
        utente.setRuolo("000001");
        request.addParameter("username", "marcobellamico");
        String message = "EU_FAIL";
        servlet.doGet(request, response);
        String result = (String) request.getAttribute("errorTest");
        assertEquals(message, result);
    }

    @Test
    public void testBanUtente5() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setUsername("marco_bellamico");
        utente.setRuolo("000001");
        Mockito.when(request.getSession().getAttribute("utente")).thenReturn(utente);
        request.addParameter("username", "roberta_esposito");
        String message = "OK";
        servlet.doGet(request, response);
        String result = (String) request.getAttribute("errorTest");
        assertEquals(message, result);
    }
}