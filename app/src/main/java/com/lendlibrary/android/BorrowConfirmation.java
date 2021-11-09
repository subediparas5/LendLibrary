package com.lendlibrary.android;

public class BookBorrowRequest {
    public String ownerUID,borrowerUID,bookName,borrow_days,borrower_location;

    public BookBorrowRequest(String ownerUID, String borrowerUID, String bookName, String borrow_days, String borrower_location) {
        this.ownerUID = ownerUID;
        this.borrowerUID = borrowerUID;
        this.bookName = bookName;
        this.borrow_days = borrow_days;
        this.borrower_location = borrower_location;
    }

    public BookBorrowRequest() {
    }

    public String getOwnerUID() {
        return ownerUID;
    }

    public void setOwnerUID(String ownerUID) {
        this.ownerUID = ownerUID;
    }

    public String getBorrowerUID() {
        return borrowerUID;
    }

    public void setBorrowerUID(String borrowerUID) {
        this.borrowerUID = borrowerUID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBorrow_days() {
        return borrow_days;
    }

    public void setBorrow_days(String borrow_days) {
        this.borrow_days = borrow_days;
    }

    public String getBorrower_location() {
        return borrower_location;
    }

    public void setBorrower_location(String borrower_location) {
        this.borrower_location = borrower_location;
    }
}
