package integration.test2;

import control.MyServletException;
import control.gestioneRecensione.SegnalaRecensioneServlet;
import model.gestioneUtente.Utente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TC_SegnalaRecensione {
    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;
    private SegnalaRecensioneServlet servlet;
    private HttpSession session;
    private Utente utente;

    @BeforeEach
    void setUp() {
        session = Mockito.mock(HttpSession.class);
        mockedRequest = Mockito.mock(HttpServletRequest.class);
        mockedResponse = Mockito.mock(HttpServletResponse.class);
        servlet = new SegnalaRecensioneServlet();
        utente = new Utente("frank", "francesco@unisa.it", "Francesco", "Ceriello", "Uomo", "1985-12-10", "010000");
        Mockito.when(mockedRequest.getSession()).thenReturn(session);
    }

    @Test
    void TC_SegnalaRecensioneServlet1() {
        Mockito.when(mockedRequest.getSession().getAttribute("utente")).thenReturn(utente);
        String message = "Utente non autorizzato";
        MyServletException exception = assertThrows(MyServletException.class, () ->
                servlet.doGet(mockedRequest, mockedResponse));
        assertEquals(message, exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        session = null;
        servlet = null;
        mockedRequest = null;
        mockedResponse = null;
    }
}