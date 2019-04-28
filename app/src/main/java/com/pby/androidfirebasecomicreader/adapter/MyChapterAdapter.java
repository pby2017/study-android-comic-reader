package com.pby.androidfirebasecomicreader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pby.androidfirebasecomicreader.R;
import com.pby.androidfirebasecomicreader.model.Chapter;

import java.util.List;

public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.MyViewHolder> {

    private Context context;
    private List<Chapter> chapterList;
    private LayoutInflater inflater;

    public MyChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_chapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.chapterNumberText.setText(chapterList.get(i).Name);
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView chapterNumberText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            chapterNumberText = itemView.findViewById(R.id.text_chapter_number);
        }
    }
}
