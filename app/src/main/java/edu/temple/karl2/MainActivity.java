package edu.temple.karl2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BookList.BookSelectedInterface {

    FragmentManager fm;

    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;
    BookList bookList;
    static int detailsIndex;       //Index of the details fragment selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //    EditText search=(EditText)findViewById(R.id.search);
        bookList=BookList.newInstance(getTestBooks());

        fm = getSupportFragmentManager();   //Get support manager (Good)

        //Set fragments for both layout modes
        // Fragments in Portrait mode
        if (findViewById(R.id.layout_portrait)!=null){

            //Transaction sets fragment list to entire book list
            fm.beginTransaction()
                    .replace(R.id.fragment_list,bookList )
                    .commit();

            twoPane = findViewById(R.id.fragment_details) != null;
            if (twoPane) {
                bookDetailsFragment = new BookDetailsFragment();
                //     BookDetailsFragment.SavedState();
                fm.beginTransaction()
                        .replace(R.id.fragment_details, bookDetailsFragment)
                        .commit();
            }
        }
        // Fragments in landscape mode
        else if(findViewById(R.id.layout_landscape)!=null){
            //Transaction sets fragment list to entire book list
            fm.beginTransaction()
                    .replace(R.id.fragment_list, BookList.newInstance(getTestBooks()))
                    .addToBackStack(null)
                    .commit();
            //Transaction to set fragment list to current fragment at selected index
            fm.beginTransaction()
                    .replace(R.id.fragment_details, BookDetailsFragment.newInstance(getTestBooks().get(detailsIndex)))
                    .addToBackStack(null)
                    .commit();

        }
    }

    private ArrayList<HashMap<String, String>> getTestBooks() {
        ArrayList<HashMap<String, String>> books = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> book;
        for (int i = 0; i < 10; i++) {
            book = new HashMap<String, String>();
            book.put("title", "Book" + i);
            book.put("author", "Author" + i);
            books.add(book);
        }
        return books;
    };
    @Override
    public void bookSelected(int index) {
        detailsIndex=index;
        if (findViewById(R.id.layout_portrait)==null){
            //bookDetailsFragment.displayBook(getTestBooks().get(index));
            fm.beginTransaction()
                    .replace(R.id.fragment_details,BookDetailsFragment.newInstance(getTestBooks().get(index)))
                    .addToBackStack(null)
                    .commit();
        }
        else if(findViewById(R.id.layout_portrait)!=null){
            fm.beginTransaction()
                    .replace(R.id.fragment_list, BookDetailsFragment.newInstance(getTestBooks().get(index)))
                    .addToBackStack(null)
                    .commit();
        }
    }

}

