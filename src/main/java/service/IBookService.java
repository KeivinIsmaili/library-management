package service;

import model.Book;

import java.util.List;

public interface IBookService {

    /* Method used for adding or updating a Book. */
    public String addOrUpdateBook(Book book);

    /* Method used for finding a Book. */
    public String findIsbnByCategory(List<String> category);

}