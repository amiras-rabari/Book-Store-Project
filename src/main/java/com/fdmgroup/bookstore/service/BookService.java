package com.fdmgroup.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fdmgroup.bookstore.data.BookRepository;
import com.fdmgroup.bookstore.model.Book;
import com.fdmgroup.bookstore.model.BookGenre;
import Exception.ItemNotFoundException;

public class BookService {
	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	public List<Book> getAllBooks() {

		return bookRepository.findAll();

	}

	public List<Book> getBooksOfGenre(BookGenre bookGenre) {

		List<Book> result = bookRepository.findAll();
		List<Book> actualresult = new ArrayList<Book>();

		for (Book e : result) {
			if (e.getBookGenre().equals(bookGenre)) {

				actualresult.add(e);

			} else {
				continue;
			}

		}
		return actualresult;

	}

	public List<Book> searchBooksByTitle(String title) {
		List<Book> result = bookRepository.findAll();

		List<Book> actualresult = new ArrayList<Book>();

		for (Book e : result) {
			if (e.getTitle().equals(title)) {

				actualresult.add(e);

			} else {
				continue;
			}

		}
		return actualresult;

	}

	public List<Book> searchBooksByAuthorName(String bookAuthorNameToSearch) {
		List<Book> result = bookRepository.findAll();
		List<Book> result1 = new ArrayList<Book>();

		for (Book e : result) {

			Pattern pattern = Pattern.compile(bookAuthorNameToSearch);
			Matcher matcher = pattern.matcher(e.getAuthor());
			boolean matchFound = matcher.find();

			if (matchFound) {

				result1.add(e);

			} else {
				continue;
			}

		}

		return result1;
	}

	public Book findById(int Id) throws ItemNotFoundException {

		Book result = bookRepository.findById(Id);
		if (result != null) {
			return result;
		}
		throw new ItemNotFoundException("book does not exist");
	}

}
