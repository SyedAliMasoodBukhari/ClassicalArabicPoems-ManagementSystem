package businessLogicLayer;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeAll;
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

/**
*
* @author Malaika Tariq
*/
class TokenBOTest {
	static BookBO objBO;
    static DALFacade dalFacade;
    static IBookDAO bookDao;
    static IPoemsDAO poemDao;
    static ITokenDAO tokenDao;
    static IRootDAO rootDao;
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
	void test() {
		fail("Not yet implemented");
	}

}
