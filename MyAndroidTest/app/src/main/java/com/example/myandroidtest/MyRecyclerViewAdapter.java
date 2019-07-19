package com.example.myandroidtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.KeywordItemHolder> {
    private LayoutInflater mInflater;
    private ArrayList<String> keyword;
    private int[] kwItemColor;
    private int sizeArrColor;

    public MyRecyclerViewAdapter(Context context, ArrayList<String> keyword) {
        this.mInflater = LayoutInflater.from(context);
        this.keyword = keyword;
        //Get color array for item background
        kwItemColor = context.getResources().getIntArray(R.array.colorKeywordItem);
        sizeArrColor = kwItemColor.length;
    }

    @NonNull
    @Override
    public KeywordItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = mInflater.inflate(R.layout.recylerview_keyword_item, viewGroup, false);
        return new KeywordItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordItemHolder keywordItemHolder, int position) {
        //In case no color for item background
        if(sizeArrColor != 0)
            keywordItemHolder.lnKeywordItem.setBackgroundColor(kwItemColor[position%sizeArrColor]);
        keywordItemHolder.tvKeyword.setText(keyword.get(position));
    }

    @Override
    public int getItemCount() {
        if(keyword != null)
            return keyword.size();
        else
            return 0;
    }

    public class KeywordItemHolder extends RecyclerView.ViewHolder{
        private LinearLayout lnKeywordItem;
        private TextView tvKeyword;

        public KeywordItemHolder(@NonNull View itemView) {
            super(itemView);
            lnKeywordItem = (LinearLayout) itemView.findViewById(R.id.ln_keyword_item);
            tvKeyword = (TextView) itemView.findViewById(R.id.tvKeyword);
        }
    }
}
