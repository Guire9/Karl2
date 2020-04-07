package edu.temple.karl2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BookList.BookSelectedInterface {

    FragmentManager fm;
    CharSequence s = "";
    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;
    BookList bookList;
    static int detailsIndex;       //Index of the details fragment selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText search=(EditText)findViewById(R.id.search);
        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editButton(search);
                fm.beginTransaction()
                        .replace(R.id.fragment_list, BookList.newInstance(getTestBooks(),editButton(search)))
                        .addToBackStack(null)
                        .commit();
            }
        });

        bookList=BookList.newInstance(getTestBooks(),s);
        fm = getSupportFragmentManager();   //Get support manager (Good)

        //Set fragments for both layout modes
        // Fragments in Portrait mode
        if (findViewById(R.id.layout_portrait)!=null){
            //Transaction sets fragment list to entire book list
            fm.beginTransaction()
                    .replace(R.id.fragment_list,bookList )
                    .commit();
        }
        // Fragments in landscape mode
        else if(findViewById(R.id.layout_landscape)!=null){
            //Transaction sets fragment list to entire book list
            fm.beginTransaction()
                    .replace(R.id.fragment_list, BookList.newInstance(getTestBooks(),s))
                    .addToBackStack(null)
                    .commit();
            //Transaction to set fragment list to current fragment at selected index
            fm.beginTransaction()
                    .replace(R.id.fragment_details, BookDetailsFragment.newInstance(getTestBooks().get(detailsIndex)))
                    .addToBackStack(null)
                    .commit();

        }
    }

    private ArrayList<Book> getTestBooks() {
        ArrayList<Book> books = new ArrayList<Book>();
        Book book;
        for (int i = 0; i < 5; i++) {
            book = new Book("Author"+i,"title"+i);
            books.add(book);
        }
        for (int i = 10; i < 15; i++) {
            book = new Book("Author"+i,"title"+i);
            books.add(book);
        }
        return books;
    }
    @Override
    public void bookSelected(int index) {
        detailsIndex=index;
        if (findViewById(R.id.layout_portrait)==null){
         //bookDetailsFragment.displayBook(getTestBooks().get(index));
            fm.beginTransaction()
                    .replace(R.id.fragment_details,BookDetailsFragment.newInstance(getTestBooks().get(detailsIndex)))
                    .addToBackStack(null)
                    .commit();
        }
        else if(findViewById(R.id.layout_portrait)!=null){
            fm.beginTransaction()
                    .replace(R.id.fragment_list, BookDetailsFragment.newInstance(getTestBooks().get(detailsIndex)))
                    .addToBackStack(null)
                    .commit();
        }
    }
    @Override
    public CharSequence editButton(EditText editText){
        CharSequence searchChar =  editText.getText();;
        return searchChar;
    }

}

