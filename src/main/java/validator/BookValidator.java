package validator;

import model.Book;

import static utils.Constants.ERROR_ISBN;
import static utils.Constants.ERROR_TAGS;
import static utils.Constants.OK;
import static utils.TerminalColorCodesProvider.ANSI_GREEN;
import static utils.TerminalColorCodesProvider.ANSI_RED;
import static utils.TerminalColorCodesProvider.ANSI_RESET;

public class BookValidator {

    /* Checker used to confirm if the isbn is composed of 13 digits. */
    public boolean isIsbnCorrect(Book book) {
        return book.getIsbn().length() == 13 ? true : false;
    }

    /* Checker used to confirm if user has provided at least a category. */
    public boolean hasCategory(Book book) {
        return !book.getCategory().isEmpty();
    }

    public String validateBook(Book book) {
        if(!isIsbnCorrect(book)) {
            return ANSI_RED + ERROR_ISBN + ANSI_RESET;
        }
        else if(!hasCategory(book)) {
            return ANSI_RED + ERROR_TAGS + ANSI_RESET;
        }
        else {
            return ANSI_GREEN + OK + ANSI_RESET;
        }
    }

}
