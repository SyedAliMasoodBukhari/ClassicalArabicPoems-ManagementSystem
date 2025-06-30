package businessLogicLayer;

import dataAccessLayer.IDALFacade;

public class IgnoreState implements ImportPoemState{
	String bookTitle;
	String poemTitle ;

	public IgnoreState(String bookTitle,String poemTitle) {
		
		this.bookTitle = bookTitle;
		this.poemTitle=poemTitle;
	}

	@Override
	public ImportPoemState processLine(IDALFacade objDAL, String line) {
		// TODO Auto-generated method stub
	if(line.contains("=========="))
	{
		return new PoemState(this.bookTitle,this.poemTitle);
	}
	else 
	{
		return new IgnoreState(this.bookTitle,this.poemTitle);
	}
	}
	

}
