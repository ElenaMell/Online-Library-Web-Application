package com.example.project;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.stereotype.Component;
import com.example.project.models.Books;
import com.example.project.repositories.BooksRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;

@Component
public class BookLoader {
	 private final BooksRepository booksRepository;

	    public BookLoader(BooksRepository booksRepository) {
	        this.booksRepository = booksRepository;
	    }

	    @Transactional
	    public void loadBooksFromCsv() throws IOException, CsvException {
	        try (CSVReader reader = new CSVReader(new InputStreamReader(
	                getClass().getResourceAsStream("/books_data.csv")))) {

	            List<String[]> lines = reader.readAll();

	            for (int i = 1; i < lines.size(); i++) {
	                String[] data = lines.get(i);

	                if (data.length < 6) {
	                    System.out.println("Skipping line " + i + " due to insufficient columns");
	                    continue;
	                }

	                Books book = new Books();
	                book.setTitle(data[0]);
	                book.setDescription(data[1]);
	                book.setAuthor(data[2]);
	                book.setImage(data[3]);
	                book.setPublishedDate(data[6]);
	                book.setGenre(data[8]);
	                book.setRating(data[4]);
	                
	                booksRepository.save(book); 
	                System.out.println("Saved book: " + book.getTitle());
	            }
	        }
	    }
	    
	   
}
