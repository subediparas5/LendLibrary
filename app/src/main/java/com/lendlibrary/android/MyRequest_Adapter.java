package com.lendlibrary.android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class BorrowRequest_Adapter extends RecyclerView.Adapter<BorrowRequest_Adapter.MyViewHolder> {

    private ArrayList<BookBorrowRequest> bookBorrowRequests;
    private Context context;

    public BorrowRequest_Adapter(Context c, ArrayList<BookBorrowRequest>b){
        context=c;
        bookBorrowRequests=b;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_borrow_req,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.bookName.setText(bookBorrowRequests.get(i).getBookName());
        String borrowerUID=bookBorrowRequests.get(i).getBorrowerUID();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(borrowerUID);
        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        User user=dataSnapshot.getValue(User.class);
                                                        myViewHolder.borrowerUsername.setText(Objects.requireNonNull(user).getUsername());

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
        myViewHolder.borrowerLocation.setText(bookBorrowRequests.get(i).getBorrower_location());
        myViewHolder.borrowDays.setText(bookBorrowRequests.get(i).getBorrow_days());
    }
    @Override
    public int getItemCount() {
        return bookBorrowRequests.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView bookName,borrowerUsername,borrowerLocation,borrowDays;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.b_book_name);
            borrowerUsername=itemView.findViewById(R.id.borrower_username);
            borrowerLocation=itemView.findViewById(R.id.borrower_location);
            borrowDays=itemView.findViewById(R.id.borrower_days);
        }
    }
}
