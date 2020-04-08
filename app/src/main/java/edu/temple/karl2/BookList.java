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

public class BookList extends Fragment {

    private static final String BOOK_LIST_KEY = "booklist";
    private static final String BOOK_List_KEY2 = "book2";
    private static final String SEARCH_KEY = "search";
    private static final String Search_KEY2 = "S2";
    private ArrayList<Book> books;
    private CharSequence mySearch="";
    BookSelectedInterface parentActivity;
    BookAdapter myAdapter;

    public BookList() {}

    public static BookList newInstance(ArrayList<Book> books, CharSequence charS) {
        BookList fragment = new BookList();
        Bundle args = new Bundle();
        args.putCharSequence(SEARCH_KEY,charS);
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
        if (savedInstanceState == null) {
            if (getArguments() != null) {
                books = (ArrayList) getArguments().getSerializable(BOOK_LIST_KEY);
                mySearch = getArguments().getCharSequence(SEARCH_KEY);
           }
        }else{
            books= (ArrayList) savedInstanceState.getSerializable(BOOK_List_KEY2);
            mySearch = savedInstanceState.getCharSequence(Search_KEY2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_book_list, container, false);
        final ListView listView =(ListView)root.findViewById(R.id.listView);
        myAdapter= new BookAdapter(getContext(), 0, books);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentActivity.bookSelected(position);
            }
        });
        displayBookList(mySearch);
        return root;
    }
    public void displayBookList(CharSequence displaySearch){
        myAdapter.getFilter().filter(displaySearch);
    }
    interface BookSelectedInterface {
        void bookSelected(int index);
        CharSequence editButton(EditText editText);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(Search_KEY2,mySearch);
        outState.putSerializable(BOOK_List_KEY2,books);
    }
}
