package service.impl;

import model.Book;
import service.IBookService;
import validator.BookValidator;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;

import static utils.Constants.OK;
import static utils.Constants.NO_BOOKS_FOUND;
import static utils.TerminalColorCodesProvider.ANSI_GREEN;
import static utils.TerminalColorCodesProvider.ANSI_RESET;

public class BookServiceImpl implements IBookService {

    private final Map<String, List<String>> booksMap;
    private BookValidator bookValidator;

    public BookServiceImpl() {
        this.booksMap = new HashMap<>();
        this.bookValidator = new BookValidator();
        initializeBooksMap();
    }

    private void initializeBooksMap() {
        booksMap.put("9226399970030", Arrays.asList("Fantasy", "Crime"));
        booksMap.put("6908784778845", Arrays.asList("Thriller", "Crime"));
        booksMap.put("8956747661663", Arrays.asList("Fantasy", "Crime", "Classics"));
        booksMap.put("9756641017244", Arrays.asList("History", "Crime"));
        booksMap.put("1208989162228", Arrays.asList("Fantasy", "Mystery"));
        booksMap.put("3023589639631", Arrays.asList("Comedy"));
        booksMap.put("1062871189621", Arrays.asList("Fantasy", "Adventure"));
        booksMap.put("3962193866095", Arrays.asList("Satyre", "Crime"));
    }

    /* method used for adding a new Book */
    @Override
    public String addOrUpdateBook(Book book) {
        String validationMessage = bookValidator.validateBook(book);
        if (validationMessage.equals(ANSI_GREEN + OK + ANSI_RESET)) {
            booksMap.put(book.getIsbn(), book.getCategory());
        }
        return validationMessage;
    }

    /* method used for finding a Book */
    @Override
    public String findIsbnByCategory(List<String> categories) {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : booksMap.entrySet()) {
            if (entry.getValue().containsAll(categories)) {
                keys.add(entry.getKey());
            }
        }
        if(!keys.isEmpty()) {
            return String.join(" ", keys);
        } else {
            return  ANSI_GREEN + NO_BOOKS_FOUND + ANSI_RESET;
        }
    }

}