package com.example.project.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.project.models.Books;
import com.example.project.models.User;
import com.example.project.repositories.BooksRepository;
import com.example.project.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class LibraryService {

	@Autowired UserRepository userRepository;
	@Autowired BooksRepository booksRepository;
	private int numberOfBorrowedBooks = 5;
	
	
	public User getUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(!user.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " Not found");
		return user.get();
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public Books getBook(Long id) {
		Optional<Books> book = booksRepository.findById(id);
		if(!book.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + id + " Not found");
		return book.get();
	}
	
	public List<Books> searchBook(String title) {
		return booksRepository.findByTitleContainingIgnoreCase(title);
	}
	
	public List<Books> getAllBooks(){
		return booksRepository.findAll();
	}
	
	public List<User> removeUser(Long userId){
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " Not found");
		userRepository.deleteById(userId);
		return userRepository.findAll();
	}
	
	public List<Books> removeBook(Long id){
		Optional<Books> book = booksRepository.findById(id);
		if(!book.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + id + " Not found");
		booksRepository.deleteById(id);
		return booksRepository.findAll();
	}
	
	@Transactional
	public User updateUser(String email, String username) {
	    Optional<User> userOpt = userRepository.findByEmail(email);
	    if (!userOpt.isPresent()) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " Not found");
	    }
	    User user = userOpt.get();
	    if (username != null) {
	        user.setUsername(username);
	    }
	    return user;
	}
	
	@Transactional
	public User createUser(String username, String email, String password) {
	    User user = new User(username, email, password);
	    return userRepository.save(user);
	}
	
	@Transactional
	public Books updateBook(Long id,String title,Boolean isBorrowed) {
		Optional<Books> bookOpt = booksRepository.findById(id);
		if(!bookOpt.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + id + " Not found");
		Books book =  bookOpt.get();
		if(title!=null) book.setTitle(title);
		return book;
	}
	
	public User addUser(User user){
		return userRepository.save(user);
	}
	
	public Books addBook(Books book){
		return booksRepository.save(book);
	}
	
	@Transactional
	public void borrowBook(Long userId,Long bookId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Books book = booksRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
		
		 
		if(book.isBorrowed()) {
			throw new RuntimeException("Book is already borrowed");
		}
		
		if(user.getBorrowedBooks().size() >= numberOfBorrowedBooks) {
			throw new RuntimeException("You can borrow only 5 books at a time");
		}
		
		book.setBorrowedBy(user);
		book.setBorrowed(true);
		user.getBorrowedBooks().add(book);
		
		booksRepository.save(book);
			
	}
	
	public List<Books> listAvailableBooks(){
		List<Books> bookList = booksRepository.findAll();
		
		return bookList.stream()
                .filter(book -> !Boolean.TRUE.equals(book.isBorrowed()))
                .collect(Collectors.toList());
	}
	
	public List<Books> getBorrowedBooks(Long userId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    return user.getBorrowedBooks(); 
	}
	
	@Transactional
	public void returnedBy(String username, Long bookId) {
	    User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
	    Books book = booksRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

	    if(!user.getBorrowedBooks().contains(book))
	    	throw new RuntimeException("This book is not borrowed by this user");
	    
	    user.getBorrowedBooks().remove(book);
	    book.setBorrowedBy(null);
	    book.setBorrowed(false);

	    booksRepository.save(book);
	}

	

	
	
}
