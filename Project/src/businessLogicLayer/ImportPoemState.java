package businessLogicLayer;

import dataAccessLayer.IDALFacade;

public interface ImportPoemState {
ImportPoemState processLine(IDALFacade objDAL,String line);
}
