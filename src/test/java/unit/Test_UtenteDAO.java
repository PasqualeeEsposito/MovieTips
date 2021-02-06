package unit;

import junit.framework.TestCase;
import model.connection.TestConPool;
import model.gestioneUtente.Utente;
import model.gestioneUtente.UtenteDAO;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test_UtenteDAO extends TestCase {
    private UtenteDAO utenteDAO;

    @BeforeEach
    protected void setUp() throws SQLException, FileNotFoundException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection con = TestConPool.getConnection();
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader("src/test/java/testmovietips.sql"));
        sr.runScript(reader);
        utenteDAO = new UtenteDAO();
    }

    @Test
    public void testAccesso1() {
        assertEquals(-1, utenteDAO.signIn("francy", "", new Utente())); // LE < 8 or LE > 255
    }

    @Test
    public void testAccesso2() {
        assertEquals(-2, utenteDAO.signIn("francy.mauro", "", new Utente())); // L’e-mail non rispetta il formato
    }

    @Test
    public void testAccesso3() {
        assertEquals(-3, utenteDAO.signIn("francy.mauro@unisa.it", "", new Utente())); // L’e-mail non esiste nel database
    }

    @Test
    public void testAccesso4() {
        assertEquals(-4, utenteDAO.signIn("francesca.mauro@unisa.it", "", new Utente())); // L'e-mail è stata bannata
    }

    @Test
    public void testAccesso5() {
        assertEquals(-5, utenteDAO.signIn("roberta.esposito@unisa.it", "Rob", new Utente())); // LP < 8 or LP > 255
    }

    @Test
    public void testAccesso6() {
        assertEquals(-6, utenteDAO.signIn("roberta.esposito@unisa.it", "Roberta!", new Utente())); // La password non rispetta il formato
    }

    @Test
    public void testAccesso7() {
        assertEquals(-7, utenteDAO.signIn("roberta.esposito@unisa.it", "Roberta1", new Utente())); // La password non corrisponde all’username
    }

    @Test
    public void testAccesso8() {
        assertEquals(1, utenteDAO.signIn("roberta.esposito@unisa.it", "Roberta1!", new Utente())); // Ok: accesso effettuato
    }

    @Test
    public void testBan1() {
        assertEquals(-1, utenteDAO.banUser("roberta_esposito", null));  // L’utente non ha effettuato l’accesso
    }

    @Test
    public void testBan2() {
        Utente utente = new Utente();
        utente.setUsername("fabrizio_ceriello");
        utente.setRuolo("001000");
        assertEquals(-2, utenteDAO.banUser("roberta_esposito", utente));  // L’utente non ricopre il ruolo di moderatore
    }

    @Test
    public void testBan3() {
        Utente utente = new Utente();
        utente.setUsername("marco_bellamico");
        utente.setRuolo("000001");
        assertEquals(-3, utenteDAO.banUser("marco_bellamico", utente));  // L'username corrisponde all’username dell’utente
    }

    @Test
    public void testBan4() {
        Utente utente = new Utente();
        utente.setUsername("marco_bellamico");
        utente.setRuolo("000001");
        assertEquals(-4, utenteDAO.banUser("robertaesposito", utente));  // L’username non esiste nel database
    }

    @Test
    public void testBan5() {
        Utente utente = new Utente();
        utente.setUsername("marco_bellamico");
        utente.setRuolo("000001");
        assertEquals(1, utenteDAO.banUser("roberta_esposito", utente));  // Ok: utente bannato
    }
}