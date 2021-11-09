package com.lendlibrary.android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class BorrowedBooksAdapter extends RecyclerView.Adapter<BorrowedBooksAdapter.MyViewHolder> {

    private ArrayList<BorrowedBooksDatabase> borrowedBooksDatabases;
    private Context context;

    public BorrowedBooksAdapter(Context c, ArrayList<BorrowedBooksDatabase>b){
        context=c;
        borrowedBooksDatabases=b;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final BorrowedBooksAdapter.MyViewHolder vHolder= new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_borrowed_books,viewGroup,false));
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.bookName.setText(borrowedBooksDatabases.get(i).getBookNamee());
        String ownerUID=borrowedBooksDatabases.get(i).getOwnerr();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(ownerUID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                myViewHolder.owner_username.setText("@"+user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return borrowedBooksDatabases.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView bookName,owner_username;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.book_namee);
            owner_username=itemView.findViewById(R.id.owner_unamee);
        }
    }
}
