package com.fdmgroup.bookstore.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.bookstore.data.OrderRepository;
import com.fdmgroup.bookstore.model.Book;
import com.fdmgroup.bookstore.model.Order;
import com.fdmgroup.bookstore.model.User;

public class OrderingService {
	private OrderRepository orderRepository;




	public OrderingService(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	public Order placeOrder(Book book, User customer) {
		Order order = new Order(book.getItemId(), book, customer, LocalDateTime.MAX);
		
		Order order1 =orderRepository.save(order);
		return order1;
		
	

	}

	public List<Order> placeOrders(List<Book> book,User customer) {

		List<Order> orders = new ArrayList<Order>();
	
		for(Book e:book) {
			Order result = this.placeOrder(e, customer);
			orders.add(result);
		}
		
		return orders;

	}

	public List<Order> getOrdersForUser(User user) {
		
		List<Order> allOrders = orderRepository.findAll();

		List<Order> ordersForUser = new ArrayList<Order>();

		for (Order e : allOrders) {
			if (e.getUser().equals(user)) {
				ordersForUser.add(e);
				
			}
			
		}
		return ordersForUser;

	}

	public List<Order> getOrdersForBook(Book book) {
		List<Order> allOrders = orderRepository.findAll();
		List<Order> ordersForBook = new ArrayList<Order>();
		for (Order e : allOrders) {
			if (e.getBookOrdered().equals(book)) {
				ordersForBook.add(e);
				
			}
			
		}return ordersForBook;
	

	}
}
