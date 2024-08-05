package handler;

import model.Book;
import service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.Constants.ADD_OR_UPDATE_COMMAND;
import static utils.Constants.SEARCH_COMMAND;
import static utils.Constants.EXIT_COMMAND;
import static utils.Constants.PROGRAM_TERMINATED;
import static utils.Constants.EXIT;
import static utils.Constants.IN;
import static utils.Constants.OUT;
import static utils.TerminalColorCodesProvider.ANSI_RESET;
import static utils.TerminalColorCodesProvider.ANSI_YELLOW;

public class InputResponseHandler {

    private static final String ADD_OR_UPDATE_REGEX = "^store (\\d+)(.*)$";
    private static final String SEARCH_REGEX = "^search(?: ([^\\s,]+)(?:, )?)+$";

    public static void start() {
        BookServiceImpl bookService = new BookServiceImpl();

        /* Add or update regex and pattern used to check if the user input complies with the syntax.
           This pattern captures the keyword 'store' followed by one or more categories.
        */
        Pattern addOrUpdatePattern = Pattern.compile(ADD_OR_UPDATE_REGEX);

        /* Search pattern used to check if the user input complies with the syntax.
           This pattern captures the keyword 'search' followed by the isbn and one or more categories.
        */
        Pattern searchPattern = Pattern.compile(SEARCH_REGEX);

        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        /* Functional tips for the user. */
        displayCommands();

        while (!userInput.equalsIgnoreCase(EXIT)) {

            System.out.print(IN);
            userInput = scanner.nextLine();

            if (processAddOrUpdate(userInput, addOrUpdatePattern, bookService)) {
                continue;
            }

            if (processSearch(userInput, searchPattern, bookService)) {
                continue;
            }
        }

        /* If the user presses exit the process finishes. */
        System.out.println(PROGRAM_TERMINATED);
        /* Close the connection to not risk memory leaks. */
        scanner.close();

    }

    private static void displayCommands() {
        System.out.println(ANSI_YELLOW + ADD_OR_UPDATE_COMMAND + ANSI_RESET);
        System.out.println(ANSI_YELLOW + SEARCH_COMMAND + ANSI_RESET);
        System.out.println(ANSI_YELLOW + EXIT_COMMAND + ANSI_RESET);
    }

    /* Checker that controls the flow of the app if the input matches the add or update pattern. */
    private static boolean processAddOrUpdate(String userInput, Pattern pattern, BookServiceImpl bookService) {
        Matcher matcher = pattern.matcher(userInput);
        if (matcher.matches()) {
            String isbn = matcher.group(1);
            /* Extract categories. */
            String categoriesPart = matcher.group(2).trim();
            List<String> categories = new ArrayList<>();
            /* Split by space to get each category. */
            String[] categoriesArray = categoriesPart.split("\\s+");
            for (String category : categoriesArray) {
                if (!category.isEmpty()) {
                    categories.add(category);
                }
            }
            System.out.println(OUT + bookService.addOrUpdateBook(new Book(isbn, categories)));
            return true;
        }
        return false;
    }

    /* Checker that controls the flow of the app if the input matches the search pattern. */
    private static boolean processSearch(String userInput, Pattern pattern, BookServiceImpl bookService) {
        Matcher matcher = pattern.matcher(userInput);
        if (matcher.matches()) {
            List<String> categories = new ArrayList<>();
            Matcher categoryMatcher = Pattern.compile("[^\\s,]+").matcher(userInput.substring(7));
            while (categoryMatcher.find()) {
                categories.add(categoryMatcher.group());
            }
            System.out.println(OUT + bookService.findIsbnByCategory(categories));
            return true;
        }
        return false;
    }

}