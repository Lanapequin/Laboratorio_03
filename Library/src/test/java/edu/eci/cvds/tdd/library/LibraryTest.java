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
        assertNull(library.loanABook(user.getId(), book.getIsbn()));
    }

    @Test
    public void loanABookIsbnTest() {
        library.addBook(book);
        library.addUser(user);
        library.loanABook(user.getId(), book.getIsbn());
        Book book1 = new Book("En Agosto nos vemos", "Gabriel Garcia Marquez", "8374043473");
        assertNull(library.loanABook(user.getId(), book1.getIsbn()));
    }

    @Test
    public void loanABookUserTest() {
        library.addBook(book);
        assertNull(library.loanABook(user.getId(), book.getIsbn()));
    }

    @Test
    public void loanABookWhenBookDoesNotAddTest() {
        library.addUser(user);
        assertNull(library.loanABook(user.getId(), book.getIsbn()));
        library.addBook(book);
        library.loanABook(user.getId(), book.getIsbn());
        Loan loan1 = library.loanABook("Santiago", "9999999999");
        assertNull(loan1);
    }

    @Test
    void loanABookWhenUserDoesNotExistTest() {
        library.addBook(book);
        library.addUser(user);
        Loan loan1 = library.loanABook("U999", "1234567890");
        assertNull(loan1);
    }


    @Test
    public void returnLoanTest() {
        library.addBook(book);
        library.addUser(user);
        Loan loan1 = library.loanABook(user.getId(), book.getIsbn());
        library.returnLoan(loan1);
        assertEquals(LoanStatus.RETURNED, loan1.getStatus());
    }

    @Test
    public void loanABookAlreadyReturnedTest() {
        library.addBook(book);
        library.addUser(user);
        Loan loan1 = library.loanABook(user.getId(), book.getIsbn());
        library.returnLoan(loan1);
        assertNotNull(library.loanABook(user.getId(), book.getIsbn()));
    }

    @Test
    public void loanABookToDifferentUserTest() {
        library.addBook(book);
        library.addUser(user);
        library.loanABook(user.getId(), book.getIsbn());
        User anotherUser = new User();
        anotherUser.setName("Carlos");
        anotherUser.setId("99999999");
        library.addUser(anotherUser);
        assertNotNull(library.loanABook(anotherUser.getId(), book.getIsbn()));
    }
    @Test
    public void returnNullWhenLoanIsNotPossibleTest() {
        Loan returnedLoan = library.returnLoan(loan);
        assertNull(returnedLoan);
    }

}
