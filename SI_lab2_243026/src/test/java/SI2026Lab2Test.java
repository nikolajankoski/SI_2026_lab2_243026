import jdk.management.jfr.FlightRecorderMXBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SI2026Lab2Test {
    @Test
    void searchBookEveryStatementTest() {
        Library library = new Library();
        library.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        library.addBook(new Book("Effective Java", "Joshua Bloch", "Programming"));

        // Test case 1 => title = ""
        assertThrows(IllegalArgumentException.class, () -> {
            library.searchBookByTitle("");
        });
        // Test case 2 => title = "Clean Code"
        List<Book> books = library.searchBookByTitle("Clean Code");
        assertNotNull(books);
        // Test case 3 => title = "Crime and Punishment"
        assertNull(library.searchBookByTitle("Crime and Punishment"));
    }

    @Test
    void borrowBookEveryBranchTest() {
        Library library = new Library();
        Book book1 = new Book("Clean Code", "Robert C. Martin", "Programming");
        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy");
        Book book3 = new Book("1984", "George Orwell", "Dystopian");
        book2.setBorrowed(true);
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        //Test case 1 => title = "", author = ""
        assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("", "");
        });
        //Test case 2 => title = "The Hobbit", author = "J.R.R. Tolkien"
        assertThrows(RuntimeException.class, () -> {
            library.borrowBook("The Hobbit", "J.R.R. Tolkien");
        });

        //Test case 3 => title = "Crime and Punishment", author = "Fyodor Dostoevsky"
        assertThrows(RuntimeException.class, () -> {
            library.borrowBook("Crime and Punishment", "Fyodor Dostoevsky");
        });

        //Test case 4 => title = "1984", author = "George Orwell"
        library.borrowBook("1984", "George Orwell");
        assertTrue(book3.isBorrowed());
    }

    @Test
    void searchBookMultipleConditionTest() {
        // if (book.getTitle().equalsIgnoreCase(title) && !book.isBorrowed())
        Library library = new Library();
        Book book1 = new Book("Clean Code", "Robert C. Martin", "Programming"); // T && T
        Book book2 = new Book("Clean Code", "Robert C. Martin", "Programming"); // T && F
        book2.setBorrowed(true);
        Book book3 = new Book("1984", "George Orwell", "Dystopian"); // F && X

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        List<Book> results = library.searchBookByTitle("Clean Code");

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Clean Code", results.get(0).getTitle());

    }

    @Test
    void borrowBookMultipleConditionTest() {
        // if (title.isEmpty() || author.isEmpty())
        Library library = new Library();

        assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("", "X"); // T || X
        });

        assertThrows(IllegalArgumentException.class, () -> {
            library.borrowBook("X", ""); // F || T
        });

        assertThrows(RuntimeException.class, () -> {
            library.borrowBook("X", "X"); // F || F
        });
    }
}

