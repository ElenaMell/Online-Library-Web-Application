package com.example.project.controllers.dto;

import java.util.List;

public class UserStripped {
	private Long id;
	private String username;
	private String email;
	private List<String> borrowedBookTitles;
	
	public UserStripped(Long id, String username, String email, List<String> borrowedBookTitles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.borrowedBookTitles = borrowedBookTitles;
    }
	
	
	public UserStripped(Long id,String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}
	
	public UserStripped(String username) {
		super();
		this.username = username;
	}
	
	public UserStripped() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}
