package businessLogicLayer;


import dataAccessLayer.IDALFacade;
import transferObject.BookTO;

public class BookTitleState  implements ImportPoemState{

	@Override
	public ImportPoemState processLine(IDALFacade objDAL,String line){
		// TODO Auto-generated method stub
		String bookName = null;
		 if (line.contains("الكتاب :")) {
		     bookName=  line.replaceAll("الكتاب :", "");
		     BookTO obj = new BookTO(bookName,"",0);
		     objDAL.insertBookDAO(obj); 
		     return  new PoemState(bookName,null);
		        }
		 else
		 {
			 return new StopState();
		 }

		    
		       
		    
		    }
	}


