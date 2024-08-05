package validator;

import model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static utils.Constants.ERROR_ISBN;
import static utils.Constants.ERROR_TAGS;
import static utils.Constants.OK;
import static utils.TerminalColorCodesProvider.ANSI_GREEN;
import static utils.TerminalColorCodesProvider.ANSI_RED;
import static utils.TerminalColorCodesProvider.ANSI_RESET;


@ExtendWith(MockitoExtension.class)
class BookValidatorTest {

    @InjectMocks
    @Spy
    private BookValidator sut;

    @Test
    void when_user_has_provided_correct_isbn_number_then_return_true() {
        Book book = new Book("1234567891011", Arrays.asList("Fantasy"));
        boolean result = sut.isIsbnCorrect(book);
        Assertions.assertEquals(true, result);
    }

    @Test
    void when_user_has_not_provided_correct_isbn_number_then_return_false() {
        Book book = new Book("12345678910", Arrays.asList("Fantasy"));
        boolean result = sut.isIsbnCorrect(book);
        Assertions.assertEquals(false, result);
    }

    @Test
    void when_user_has_provided_any_category_then_return_true() {
        Book book = new Book("12345678910", Arrays.asList("Fantasy"));
        boolean result = sut.hasCategory(book);
        Assertions.assertEquals(true, result);
    }

    @Test
    void when_user_has_not_provided_any_category_then_return_false() {
        Book book = new Book("12345678910", Arrays.asList());
        boolean result = sut.hasCategory(book);
        Assertions.assertEquals(false, result);
    }

    @Test
    public void when_isbn_is_in_wrong_format_then_return_appropriate_error_message() {
        Book book = new Book("123456789012", Arrays.asList("Fiction"));
        String result = sut.validateBook(book);
        Assertions.assertEquals(ANSI_RED + ERROR_ISBN + ANSI_RESET, result);
    }

    @Test
    public void when_user_has_not_provided_any_category_then_return_appropriate_error_message() {
        Book book = new Book("1234567890123", Arrays.asList());
        String result = sut.validateBook(book);
        Assertions.assertEquals(ANSI_RED + ERROR_TAGS + ANSI_RESET, result);
    }

    @Test
    public void when_user_has_provided_right_isbn_format_and_categories_then_return_appropriate_message() {
        Book book = new Book("1234567890123", Arrays.asList("Fantasy"));
        String result = sut.validateBook(book);
        Assertions.assertEquals(ANSI_GREEN + OK + ANSI_RESET, result);
    }


}