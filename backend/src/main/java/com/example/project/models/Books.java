package com.example.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Books {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(length = 500)
	@Size(max = 500)
	private String title;
	@Column(length = 10000)
	@Size(max = 10000)
	private String description;
	private String author;
	private String genre;
	private String publishedDate;
	private String rating;
	private String image;
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User borrowedBy;
	@Column(nullable = false)
	private boolean borrowed = false;
	
	
	public Books(Long id, String title, String description, String author, String genre, String publishedDate,String rating,
			String image, User borrowedBy, boolean borrowed) {
		super();
		this.id = id;
		setTitle(title);
		setDescription(description);
		setAuthor(author);
		setGenre(genre);
		this.publishedDate = publishedDate;
		this.rating = rating;
		this.image = image;
		this.borrowedBy = borrowedBy;
		this.borrowed = borrowed;
	}

	public Books(String title) {
		this.title = title;
	}
	
	public Books() {
	}
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
        if (description != null && description.length() > 10000) {
            System.out.println("Προσοχή: Η περιγραφή κόπηκε στους 10000 χαρακτήρες.");
            this.description = description.substring(0, 10000);
        } else {
            this.description = description;
        }
    }

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
        if (author != null && author.length() > 255) {
            System.out.println("Προσοχή: Το όνομα του συγγραφέα κόπηκε στους 255 χαρακτήρες.");
            this.author = author.substring(0, 255);
        } else {
            this.author = author;
        }
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
        if (genre != null && genre.length() > 255) {
            System.out.println("Προσοχή: Το όνομα της κατηγορίας κόπηκε στους 255 χαρακτήρες.");
            this.genre = genre.substring(0, 255);
        } else {
            this.genre = genre;
        }
	}

	public User getBorrowedBy() {
		return borrowedBy;
	}

	public void setBorrowedBy(User borrowedBy) {
		this.borrowedBy = borrowedBy;
	}

	public boolean isBorrowed() {
		return borrowed;
	}

	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
        if (title != null && title.length() > 500) {
            System.out.println("Προσοχή: Ο τίτλος κόπηκε στους 500 χαρακτήρες.");
            this.title = title.substring(0, 500);
        } else {
            this.title = title;
        }
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	} 
	
	
	
	
}
