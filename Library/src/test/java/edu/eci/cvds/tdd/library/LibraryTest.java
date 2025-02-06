package edu.eci.cvds.tdd.library;

import org.junit.jupiter.api.BeforeEach;
import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.Library;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryTest {
    Library library;
    @BeforeEach
    void setUp() {
        library= new Library();
    }

    @Test
    public void addBookTest(){
        Book book = new Book("100 años de soledad", "Gabriel Garcia Marquez", "837408273");
        assertTrue(library.addBook(book));
    }
}
