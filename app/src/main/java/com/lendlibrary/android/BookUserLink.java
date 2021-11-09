package com.lendlibrary.android;

public class BookUserLink {

    private String Genre,Writer,Title,UID;

    public BookUserLink() {
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public BookUserLink(String genre, String writer, String title, String UID) {
        Genre = genre;
        Writer = writer;
        Title = title;
        this.UID = UID;
    }
}
