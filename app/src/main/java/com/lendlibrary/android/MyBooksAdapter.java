package com.lendlibrary.android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<BookUserLink> bookUserLinks;
    private Context context;

    public MyAdapter(Context c, ArrayList<BookUserLink>b){
        context=c;
        bookUserLinks=b;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_books,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bookName.setText(bookUserLinks.get(i).getTitle());
        myViewHolder.bookWriter.setText(bookUserLinks.get(i).getWriter());
    }
    @Override
    public int getItemCount() {
        return bookUserLinks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView bookName,bookWriter;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.book_name);
            bookWriter=itemView.findViewById(R.id.book_writer);
        }
    }
}
