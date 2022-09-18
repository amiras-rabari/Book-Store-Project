package com.fdmgroup.bookstore.data;

import com.fdmgroup.bookstore.model.Book;

public interface BookRepository extends Searchable<Book>, Removeable<Book>, Persistable<Book> {

}
