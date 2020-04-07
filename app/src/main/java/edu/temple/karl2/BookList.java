package edu.temple.karl2;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class BookList extends Fragment {

    private static final String BOOK_LIST_KEY = "booklist";
    private static final String Search_Key = "search";
    private ArrayList<Book> books;
    private CharSequence mySearch;
    BookSelectedInterface parentActivity;
    BookAdapter myAdapter;

    public BookList() {}

    public static BookList newInstance(ArrayList<Book> books, CharSequence search2) {
        BookList fragment = new BookList();
        Bundle args = new Bundle();
        args.putCharSequence(Search_Key,search2);
        args.putSerializable(BOOK_LIST_KEY, books);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BookSelectedInterface) {
            parentActivity = (BookSelectedInterface) context;
        } else {
            throw new RuntimeException("Please implement the required interface(s)");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            books = (ArrayList) getArguments().getSerializable(BOOK_LIST_KEY);
            mySearch = getArguments().getCharSequence(Search_Key);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View root =  inflater.inflate(R.layout.fragment_book_list, container, false);

        final ListView listView =(ListView)root.findViewById(R.id.listView);
        myAdapter= new BookAdapter(getContext(), 0, books);        //Create new adapter
        listView.setAdapter(myAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentActivity.bookSelected(position);
            }
        });

        myAdapter.getFilter().filter(mySearch);
        return root;
    }

    public void displayBookList(){

    }

    interface BookSelectedInterface {
        void bookSelected(int index);
        CharSequence editButton(EditText editText);

    }
}
