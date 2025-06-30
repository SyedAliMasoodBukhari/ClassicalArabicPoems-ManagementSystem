package businessLogicLayer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import DAOStub.BookDAOStub;
import DAOStub.PoemDaoStub;
import DAOStub.RootDAOStub;
import DAOStub.TokenDAOStub;
import dataAccessLayer.DALFacade;
import dataAccessLayer.IBookDAO;
import dataAccessLayer.IPoemsDAO;
import dataAccessLayer.IRootDAO;
import dataAccessLayer.ITokenDAO;
import transferObject.BookTO;

/**
 *
 * @author Malaika Tariq
 */
public class BookBOTest {
    static BookBO objBO;
    static DALFacade dalFacade;
    static BookDAOStub bookDao;
    static IPoemsDAO poemDao;
    static ITokenDAO tokenDao;
    static IRootDAO rootDao;
  

    public BookBOTest() {
        
    }
    
    @BeforeAll
    public static void setUpClass() {
        rootDao=new RootDAOStub();
        poemDao=new PoemDaoStub();
        tokenDao=new TokenDAOStub();
        bookDao=new BookDAOStub();
        dalFacade= new DALFacade(poemDao,bookDao,rootDao,tokenDao);
        objBO=new BookBO(dalFacade);
    }
    @Test
    @DisplayName("Inserting Book Test")
    void testingBookInsertion()
    {
        BookTO bookTO =new BookTO("اسلامي","همزا",5);
        Assertions.assertTrue(objBO.insertBookDAO(bookTO));
    }
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    
}
	