package edu.eci.cvds.tdd.library;

import org.junit.jupiter.api.BeforeEach;
import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.loan.*;
import edu.eci.cvds.tdd.library.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    Library library;
    Book book;
    Loan loan;
    User user;
    @BeforeEach
    void setUp() {
        library= new Library();
        book = new Book("100 años de soledad", "Gabriel Garcia Marquez", "837408273");
        loan = new Loan();
        user = new User();
        user.setName("Santiago");
        user.setId("83726482");
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setReturnDate(LocalDateTime.now().plusDays(3));
    }

    @Test
    public void addNewBookTest(){
        assertTrue(library.addBook(book));
    }

    @Test
    public void addOldBookTest(){
        library.addBook(book);
        assertTrue(library.addBook(book));
    }

    @Test
    public void loanABookTest(){
        library.addBook(book);
        library.addUser(user);
        assertNotNull(library.loanABook(user.getId(), book.getIsbn()));
    }

    @Test

    public void loanABookActiveTest() {
        library.addBook(book);
        library.addUser(user);
        library.loanABook(user.getId(), book.getIsbn());
        assertNull(library.loanABook(user.getId(), book.getIsbn()));
    }

    @Test
    public void returnLoanTest(){
        library.addBook(book);
        library.addUser(user);
        Loan loan1 = library.loanABook(user.getId(), book.getIsbn());
        library.returnLoan(loan1);
        assertEquals(LoanStatus.RETURNED, loan1.getStatus());
    }

}
