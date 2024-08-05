package service;

import model.Book;
import org.junit.jupiter.api.BeforeEach;
import service.impl.BookServiceImpl;
import validator.BookValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static utils.Constants.ERROR_ISBN;
import static utils.Constants.ERROR_TAGS;
import static utils.Constants.OK;
import static utils.Constants.NO_BOOKS_FOUND;
import static utils.TerminalColorCodesProvider.ANSI_GREEN;
import static utils.TerminalColorCodesProvider.ANSI_RED;
import static utils.TerminalColorCodesProvider.ANSI_RESET;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private final static String COMEDY = "Comedy";
    private final static String DRAMA = "Drama";
    private final static String FANTASY = "Fantasy";

    @InjectMocks
    @Spy
    private BookServiceImpl sut;

    @Mock
    private BookValidator bookValidator;

    @BeforeEach
    public void setUp() {}

    @Test
    void when_user_has_not_provided_correct_isbn_number_then_return_appropriate_error_message() {
        Book book = new Book("123", Arrays.asList(FANTASY));

        String validationMessage = ANSI_RED + ERROR_ISBN + ANSI_RESET;
        when(bookValidator.validateBook(book)).thenReturn(validationMessage);

        String result = sut.addOrUpdateBook(book);
        Assertions.assertEquals(result, validationMessage);
    }

    @Test
    void when_user_has_not_provided_any_category_then_return_appropriate_error_message() {
        Book book = new Book("1234567891011", Arrays.asList());

        String validationMessage = ANSI_RED + ERROR_TAGS + ANSI_RESET;
        when(bookValidator.validateBook(book)).thenReturn(validationMessage);

        String result = sut.addOrUpdateBook(book);
        Assertions.assertEquals(result, validationMessage);
    }

    @Test
    void when_user_has_provided_right_isbn_and_categories_then_returns_appropriate_success_message() {
        Book book = new Book("1234567891011", Arrays.asList("Fantasy"));

        String validationMessage = ANSI_GREEN + OK + ANSI_RESET;
        when(bookValidator.validateBook(book)).thenReturn(validationMessage);

        String result = sut.addOrUpdateBook(book);
        Assertions.assertEquals(result, validationMessage);
    }

    @Test
    void when_user_has_searched_right_categories_it_returns_a_list_of_books() {
        List<String> categories = Arrays.asList(COMEDY);
        sut = new BookServiceImpl();
        String result = sut.findIsbnByCategory(categories);
        Assertions.assertEquals("3023589639631", result);

    }

    @Test
    void when_user_has_searched_categories_that_are_not_available_it_returns_an_appropriate_message() {
        List<String> categories = Arrays.asList(DRAMA);
        sut = new BookServiceImpl();
        String result = sut.findIsbnByCategory(categories);
        Assertions.assertEquals(ANSI_GREEN + NO_BOOKS_FOUND + ANSI_RESET, result);
    }

}