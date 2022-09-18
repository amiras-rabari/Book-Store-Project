package com.fdmgroup.bookstore.data;

public interface Persistable<T> {
	
	T save(T t);
}
