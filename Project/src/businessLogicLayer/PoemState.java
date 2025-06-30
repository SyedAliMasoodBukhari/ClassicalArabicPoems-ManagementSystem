package businessLogicLayer;

import dataAccessLayer.IDALFacade;
import dataAccessLayer.IPoemsDAO;
import transferObject.BookTO;
import transferObject.PoemsTO;

public class PoemState implements ImportPoemState {
 
	String bookTitle;
	String poemTitle ;

	public PoemState(String bookTitle,String poemTitle) {
		
		this.bookTitle = bookTitle;
		this.poemTitle=poemTitle;
	}


	@Override
	public ImportPoemState processLine(IDALFacade objDAL,String line)
 {
		if (line.contains("]") && line.contains("[") && !line.contains(")") && !line.contains("("))
        {
           line = line.substring(1, line.length() - 1);
           this.poemTitle = line;
           objDAL.addPoemDAO(this.bookTitle, this.poemTitle);
           return new PoemState(this.bookTitle,this.poemTitle);
        }
		else if (line.contains(")") && line.contains("(") && line.contains("...")) 
		  {// TODO Auto-generated method stub
		 line = line.substring(1, line.length() - 2);
       String[] misra = line.split("\\.\\.\\.");
       if (misra.length < 1) { // if only 1 misra
           PoemsTO objPoemTo = new PoemsTO();
           objPoemTo.setBookTitle(this.bookTitle);
           objPoemTo.setPoemTitle(this.poemTitle);
           objPoemTo.setMisra1(misra[0]);
           objPoemTo.setMisra2(null);
           objPoemTo.setMisra1WithoutAraab(objPoemTo.getMisra1().replaceAll("[\\u064B-\\u0652\\u0670\\u0671]", ""));
           objPoemTo.setMisra2WithoutAraab(null);
           objDAL.insertVerseDAO(objPoemTo);
           return new PoemState(this.bookTitle,this.poemTitle);
          
       } else if (misra.length > 1) { // if there are 2 misras
           PoemsTO objPoemTo = new PoemsTO();
           objPoemTo.setBookTitle(this.bookTitle);
           objPoemTo.setPoemTitle(this.poemTitle);
           objPoemTo.setMisra1(misra[0]);
           objPoemTo.setMisra2(misra[1]);
           objPoemTo.setMisra1WithoutAraab(objPoemTo.getMisra1().replaceAll("[\\u064B-\\u0652\\u0670\\u0671]", ""));
           objPoemTo.setMisra2WithoutAraab(objPoemTo.getMisra2().replaceAll("[\\u064B-\\u0652\\u0670\\u0671]", ""));
           objDAL.insertVerseDAO(objPoemTo);
          
           return new PoemState(this.bookTitle,this.poemTitle);
       }
		  }
		else if(line.contains("___"))
		{
			return new IgnoreState(this.bookTitle,this.poemTitle);
		}
		return new PoemState(this.bookTitle,this.poemTitle);
	}

}
