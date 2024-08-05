package model;

import java.util.List;
import java.util.Objects;

public class Book {

    private String isbn;
    private List<String> category;

    public Book(String isbn, List<String> category) {
        this.isbn = isbn;
        this.category = category;
    }

    public Book() {}

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public List<String> getCategory() { return category; }
    public void setCategory(List<String> category) { this.category = category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(category, book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, category);
    }
}