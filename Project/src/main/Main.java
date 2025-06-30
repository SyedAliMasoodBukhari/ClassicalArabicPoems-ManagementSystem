package main;

import businessLogicLayer.BLLFacade;
import businessLogicLayer.BookBO;
import businessLogicLayer.IBLLFacade;
import businessLogicLayer.IBookBO;
import businessLogicLayer.IPoemBO;
import businessLogicLayer.IRootBO;
import businessLogicLayer.ITokenBO;
import businessLogicLayer.PoemBO;
import businessLogicLayer.RootBO;
import businessLogicLayer.TokenBO;
import dataAccessLayer.BookDAO;
import dataAccessLayer.DALFacade;
import dataAccessLayer.IBookDAO;
import dataAccessLayer.IDALFacade;
import dataAccessLayer.IRootDAO;
import dataAccessLayer.PoemsDAO;
import dataAccessLayer.RootDAO;
import presentationLayer.GUI_PO;
import dataAccessLayer.IPoemsDAO;
import dataAccessLayer.ITokenDAO;
import dataAccessLayer.TokensDAO;

public class Main {

    public static void main(String[] args) {
        XAMPPManager.startXAMPP();
        IPoemsDAO ipoemDAO = new PoemsDAO();
        IBookDAO ibookDao = new BookDAO();
        IRootDAO irootDao = new RootDAO();
        ITokenDAO itokenDao = new TokensDAO();
        IDALFacade idalF = new DALFacade(ipoemDAO, ibookDao, irootDao, itokenDao);
        IBookBO bookBo = new BookBO(idalF);
        IPoemBO objPoem = new PoemBO(idalF);
        IRootBO irootBo = new RootBO(idalF);
        ITokenBO itokenBo = new TokenBO(idalF);
        IBLLFacade bllF = new BLLFacade(objPoem, bookBo, irootBo, itokenBo);
        GUI_PO.applyLaFOnJFrame();
        java.awt.EventQueue.invokeLater(() -> {
            new GUI_PO(bllF).setVisible(true);
        });
    }
}
