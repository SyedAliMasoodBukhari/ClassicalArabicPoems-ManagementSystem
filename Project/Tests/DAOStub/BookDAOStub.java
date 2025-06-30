/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOStub;

import dataAccessLayer.IBookDAO;
import java.util.ArrayList;
import transferObject.BookTO;

/**
 *
 * @author Malaika Tariq
 */
public class BookDAOStub implements IBookDAO{
    
    ArrayList <BookTO> dummyBookDao=new  ArrayList <BookTO> ();
    public BookDAOStub()
    {
        dummyBookDao.add(new BookTO("أوديب","علي",5));
        dummyBookDao.add(new BookTO("مخدرات اسلام","قدرية حسين",2));
        dummyBookDao.add(new BookTO("تاريخ العرب","مومن",2));
        dummyBookDao.add(new BookTO(" اسلام","معيد",2));
        dummyBookDao.add(new BookTO("اربي","اهمد",0));
        
    }

    @Override
    public boolean insertBookDAO(BookTO bookTO) {
        if(bookTO.getBookName()==null){
            return false;
        }
        else{
            dummyBookDao.add(bookTO);
        return true ;
        } 
        
    }

    @Override
    public boolean updateBookDAO(String currBookName, String newBookName, String newBookAuthor) {
     for(BookTO obj: dummyBookDao)
     {
         if(obj.getBookName().equals(currBookName))
         {
             obj.setBookName(newBookName);
             return true ;
         }
     }
       return false; 
    }

    @Override
    public boolean deleteBookDAO(String bookName) {
       return false;
    }

    @Override
    public ArrayList<BookTO> getAllBooksDAO() {
      
        if(dummyBookDao==null)
        {
           return null; 
        }
        else
        {
            return dummyBookDao;
        }
         }
    
}
