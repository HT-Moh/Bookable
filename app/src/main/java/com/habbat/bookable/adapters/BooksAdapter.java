package com.habbat.bookable.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habbat.bookable.R;
import com.habbat.bookable.models.Item;
import com.habbat.bookable.models.VolumeInfo;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hackolos on 18.12.17.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.Book>{

    List<Item> items;
    Context context;


    public BooksAdapter(List<Item> items, Context context){
        this.items = items;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public Book onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.books_card_view, viewGroup, false);
        Book pvh = new Book(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(Book bookHolder, int i) {
        VolumeInfo volumeinfo = items.get(i).getVolumeInfo();
        bookHolder.title.setText(volumeinfo.getTitle());
        bookHolder.author.setText(volumeinfo.getAuthors().get(0));
        if(volumeinfo.getImageLinks().getSmallThumbnail()!=null){
            Glide.with(context)
                    .load(volumeinfo.getImageLinks().getSmallThumbnail())
                    .into(bookHolder.bookPhoto);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class Book extends RecyclerView.ViewHolder {
        @BindView(R.id.cv)
        CardView cv;
        @BindView(R.id.bookTitle)
        TextView title;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.bookPhoto)
        ImageView bookPhoto;

        Book(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            title = (TextView)itemView.findViewById(R.id.bookTitle);
            author = (TextView)itemView.findViewById(R.id.author);
            bookPhoto = (ImageView)itemView.findViewById(R.id.bookPhoto);
        }
    }

}