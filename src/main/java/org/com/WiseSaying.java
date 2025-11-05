package org.com;

public class WiseSaying {
    private int id;
    private String content;
    private String author;

    WiseSaying() {}

    WiseSaying(int id, String content, String author) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
