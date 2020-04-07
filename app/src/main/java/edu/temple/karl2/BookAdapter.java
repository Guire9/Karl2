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
import java.util.HashMap;

public class BookAdapter extends ArrayAdapter implements Filterable {
    Context context;
    ArrayList<HashMap<String, String>> books;       //Original data
    ArrayList<HashMap<String, String>> data;           //Data to be filtered
    BookFilter myFilter;


    public BookAdapter( Context context, int resource,ArrayList books) {
        super(context, resource);
        this.context = context;
        this.books = books;
        this.data=books;
        myFilter=new BookFilter(books);     //Set filter
    }

    @Override
    public Filter getFilter() {
        return myFilter;
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

        titleTextView.setText(((HashMap<String, String>) getItem(position)).get("title"));
        authorTextView.setText(((HashMap<String, String>) getItem(position)).get("author"));

        return convertView;
    }

    private class BookFilter extends Filter {
        ArrayList<HashMap<String, String>> allBooks;
        public BookFilter(ArrayList<HashMap<String, String>> b) {
            this.allBooks=b;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String stringToFilter = constraint.toString().toLowerCase();

            final ArrayList<HashMap<String, String>>  lookUpList = this.allBooks;

            int count = lookUpList.size();
            final ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>(count);

            String filterableString ;
            //Filter here
            for (int i = 0; i < count; i++) {
                filterableString = lookUpList.get(i).get("title");
                if (filterableString.toLowerCase().contains(stringToFilter)) {
                    newList.add(lookUpList.get(i));
                }
            }
            //Set results
            FilterResults results = new FilterResults();        //To return
            results.values = newList;
            results.count = newList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data=(ArrayList<HashMap<String, String>> )results.values;       //Keep data filtered, updated
            notifyDataSetChanged();
        }

    }
}
