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
import com.habbat.bookable.activities.BooksRecycledListView;
import com.habbat.bookable.models.Item;
import com.habbat.bookable.models.VolumeInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HT-Moh on 18.12.17.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.Book>{

    private List<Item> items;
    Context context;

    public void setItems(List<Item> items){
        //this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(int position);
    }

    public BooksAdapter(List<Item> items, Context context){
        this.items = items;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return items==null? 0:items.size();
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
        bookHolder.author.setText((volumeinfo!=null && volumeinfo.getAuthors()!=null && volumeinfo.getAuthors().size()>0)?volumeinfo.getAuthors().get(0).trim():"");
        if(volumeinfo!=null && volumeinfo.getImageLinks()!=null && volumeinfo.getImageLinks().getSmallThumbnail()!=null){
            Glide.with(context)
                    .load(volumeinfo.getImageLinks().getSmallThumbnail())
                    .into(bookHolder.bookPhoto);
        }
        bookHolder.kind.setText(items.get(i).getKind());
        bookHolder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BooksRecycledListView bookActivity = (BooksRecycledListView)context;
                return bookActivity.onItemLongClicked(i);
            }
        });
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
        @BindView(R.id.kind)
        TextView kind;
        @BindView(R.id.bookPhoto)
        ImageView bookPhoto;

        Book(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}