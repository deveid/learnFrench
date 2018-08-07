package com.example.android.lang;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david.lang.R;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mcolorResource;

    public WordAdapter(Activity context, ArrayList<Word> words,int colorResource){
        super(context,0,words);
        mcolorResource=colorResource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Word currentWord=getItem(position);

        TextView frenchWord=(TextView) listItemView.findViewById(R.id.listview1);
        frenchWord.setText(currentWord.getFrenchTranslation());

        TextView englishWord=(TextView) listItemView.findViewById(R.id.listview2);
        englishWord.setText(currentWord.getDefaultTranslation());

        ImageView image=(ImageView) listItemView.findViewById(R.id.image);
        if (currentWord.hasImage()) {
            //set imageview if an image exist
            image.setImageResource(currentWord.getImageResource());
            //makes sure the view is visible
            image.setVisibility(View.VISIBLE);
        }
        else {
            //hides imageview if no image exist
            image.setVisibility(View.GONE);
        }
        View textContainerr=listItemView.findViewById(R.id.textContainer);
        int color= ContextCompat.getColor(getContext(),mcolorResource);
        textContainerr.setBackgroundColor(color);

        return listItemView;
    }
}
