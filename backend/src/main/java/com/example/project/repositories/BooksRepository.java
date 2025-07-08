package com.example.project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.models.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books,Long>{

	List<Books> findByTitleContainingIgnoreCase(String title);

	Optional<Books> findByTitle(String title);	
}
