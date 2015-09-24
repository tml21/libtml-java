package com.example.mk.tmlexample;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//ListFragment for MainActivity
public class MainListFragment extends android.support.v4.app.ListFragment {
    private int position = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Create an array adapter for the list view, using dblist-items
        setListAdapter(new ArrayAdapter<DataBaseEntry>(getActivity(), layout, ((MainActivity) getActivity()).databaseItemList));
        setHasOptionsMenu(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        //so as not to leave an item selected after something was done
        if(position != -1) {
            getListView().setItemChecked(position, false);
        }
        //after user may have added or deleted or edited an item from the list
        ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Set the item as checked to be highlighted
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setItemChecked (position, true);
        this.position = position;
        // Notify the parent activity of selected item
        ((MainActivity) getActivity()).onItemSelected(position);
    }

}
