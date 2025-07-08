package com.example.project.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	@Column(unique = true)
	private String email;
	private List<String> borrowedBookTitles;
	@OneToMany(mappedBy="borrowedBy")
	@JsonManagedReference
	private List<Books> borrowedBooks = new ArrayList<>();
	
	public User(Long id,String username,String password, String email, List<String> borrowedBookTitles) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.borrowedBookTitles = borrowedBookTitles;
	}
	
	public User(String username, String email,String password) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public User(String username, String email) {
		this.username = username;
		this.email = email;
	}
	
	public User(String username) {
		this.username = username;
	}
	
	public User() {
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getBorrowedBookTitles() {
		return borrowedBookTitles;
	}

	public void setBorrowedBookTitles(List<String> borrowedBookTitles) {
		this.borrowedBookTitles = borrowedBookTitles;
	}

	public List<Books> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(List<Books> bookList) {
		this.borrowedBooks = bookList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.trim();
	}	
	
}
