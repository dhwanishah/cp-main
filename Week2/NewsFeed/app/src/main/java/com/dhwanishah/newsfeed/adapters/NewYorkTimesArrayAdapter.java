package com.dhwanishah.newsfeed.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dhwanishah.newsfeed.models.NewYorkTimeArticle;

import java.util.List;

/**
 * Created by DhwaniShah on 3/14/17.
 */

public class NewYorkTimesArrayAdapter extends ArrayAdapter<NewYorkTimeArticle> {
    public NewYorkTimesArrayAdapter(Context context, List<NewYorkTimeArticle> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }
}
