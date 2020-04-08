package edu.temple.karl2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class BookAdapter extends ArrayAdapter implements Filterable {
    Context context;
    ArrayList<Book> books;
    ArrayList<Book> data;
    BookFilter myFilter;

    public BookAdapter( Context context, int resource,ArrayList books) {
        super(context, resource);
        this.context = context;
        this.books = books;
        this.data=books;
        myFilter=new BookFilter(books);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView titleTextView, authorTextView;

        if (!(convertView instanceof LinearLayout)) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_adapter, parent, false);
        }

        titleTextView = convertView.findViewById(R.id.titleTextView);
        authorTextView = convertView.findViewById(R.id.authorTextView);

        titleTextView.setText(books.get(position).title);
        authorTextView.setText(books.get(position).author);
        return convertView;
    }
    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private class BookFilter extends Filter {
        ArrayList<Book> allBooks;
        public BookFilter(ArrayList<Book> b) {
            this.allBooks=b;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            String stringToFilter = constraint.toString().toLowerCase();
            final ArrayList<Book>  lookUpList = this.allBooks;
            int count = lookUpList.size();
            final ArrayList<Book> newList = new ArrayList<Book>(count);

            String filterableString ;
            for (int i = 0; i < count; i++) {
                filterableString = lookUpList.get(i).getTitle();
                if (filterableString.toLowerCase().contains(stringToFilter)) {
                    newList.add(lookUpList.get(i));
                }
            }
            for (int i = 0; i < count; i++) {
                filterableString = lookUpList.get(i).getAuthor();
                if (filterableString.toLowerCase().contains(stringToFilter)) {
                    newList.add(lookUpList.get(i));
                }
            }
            results.values = newList;
            results.count = newList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data=(ArrayList<Book> )results.values;
            notifyDataSetChanged();
        }

    }
}
