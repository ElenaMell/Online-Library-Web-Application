package com.example.project.controllers;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.BookLoader;
import com.example.project.controllers.dto.UserStripped;
import com.example.project.models.Books;
import com.example.project.models.User;
import com.example.project.services.LibraryService;
import com.opencsv.exceptions.CsvException;

@CrossOrigin(origins = "*")
@RestController
public class LibraryController {
	
	@Autowired LibraryService libraryService;
	private final BookLoader loader;
	
	
	public LibraryController(BookLoader loader) {
		super();
		this.loader = loader;
	}
	
	@GetMapping("/getuserbyemail")
	public UserStripped getUserByEmail(@RequestParam String email) {
	    User user = libraryService.getUserByEmail(email);
	    List<String> titles = user.getBorrowedBooks()
	                              .stream()
	                              .map(Books::getTitle)
	                              .toList();
	    return new UserStripped(user.getId(), user.getUsername(), user.getEmail(), titles);
	}
	
	
	@GetMapping("/getallusers")
	public List<UserStripped> getAllUsers() {
		List<User> list = libraryService.getAllUsers();
		return list.stream().map(user -> new UserStripped(user.getId(),user.getUsername(),user.getEmail())).collect(Collectors.toList());
	}
	
	@GetMapping("/getbook")
	public Books getBook(@RequestParam Long id) {
		return libraryService.getBook(id);
	}
	
	@GetMapping("/searchbook/{title}")
	public List<Books> searchBook(@PathVariable String title) {
		return libraryService.searchBook(title);	}
	
	@GetMapping("/getallbooks")
	public List<Books> getAllBooks() {
	    return libraryService.getAllBooks().stream().limit(500).collect(Collectors.toList()); //limit(500) γιατί είναι πάρα πολλά τα βιβλία και δεν τα φορτώνει αλλιώς
	}
	
	@DeleteMapping("/deleteuser")
	public List<UserStripped> removeUser(@RequestParam Long userId){
		List<User> list = libraryService.removeUser(userId);
		return list.stream().map(user -> new UserStripped(user.getId(),user.getUsername(),user.getEmail())).collect(Collectors.toList());
	}
	
	@DeleteMapping("/deletebook")
	public List<Books> removeBook(@RequestParam Long id){
		return libraryService.removeBook(id);
	}
	
	@PostMapping("/updateuser")
	public User updateUser(@RequestBody User user) {
	    return libraryService.updateUser(user.getEmail(), user.getUsername());
	}
	
	@PutMapping("/updatebook")
	public Books updateBook(@RequestParam Long id, @RequestParam (required=false) String title,@RequestParam (required=false) Boolean borrowed) {
		return libraryService.updateBook(id, title,borrowed);
	}
	
	@PostMapping("/adduser")
	public ResponseEntity<UserStripped> addUser(@RequestBody User user) {
	    try {
	        User us = libraryService.addUser(user);
	        return ResponseEntity.ok(new UserStripped(us.getId(), us.getUsername(), us.getEmail()));
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@PostMapping("/createuser")
	public ResponseEntity<UserStripped> createUser(@RequestBody Map<String, String> body) {
	    String username = body.get("username");
	    String email = body.get("email");
	    String password = body.get("password");

	    if (username == null || username.trim().isEmpty() ||
	        email == null || email.trim().isEmpty() ||
	        password == null || password.trim().isEmpty()) {
	        return ResponseEntity.badRequest().body(null);
	    }

	    User user = libraryService.createUser(username, email, password);
	    return ResponseEntity.ok(new UserStripped(user.getId(), user.getUsername(), user.getEmail()));
	}
	
	@PostMapping("/addbook")
	public Books addBook(@RequestBody Books book) {
	    return libraryService.addBook(book);
	}
	
	@PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        libraryService.borrowBook(userId, bookId);
        return ResponseEntity.ok("Book borrowed successfully!");
    }
	
	@GetMapping("/getavailablebooks")
	public List<Books> listAvailableBooks(){
		return libraryService.listAvailableBooks().stream().limit(500).collect(Collectors.toList()); //limit(500) γιατί είναι πάρα πολλά τα βιβλία και δεν τα φορτώνει αλλιώς 
	}
	
	@GetMapping("/borrowed")
	public List<Books> getBorrowedBooks(@RequestParam Long userId) {
	    return libraryService.getBorrowedBooks(userId);
	}
	
	@PostMapping("/returned")
    public ResponseEntity<String> returnedBook(@RequestParam String username, @RequestParam Long bookId) {
        libraryService.returnedBy(username, bookId);
        return ResponseEntity.ok("Book returned successfully!");
    }
	
	 @GetMapping("/import-books")
	    public String importBooks() {
	        try {
				loader.loadBooksFromCsv();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CsvException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return "Books imported!";
	    }
	
}
