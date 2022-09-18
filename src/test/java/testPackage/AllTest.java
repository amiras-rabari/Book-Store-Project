package testPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fdmgroup.bookstore.data.BookRepository;
import com.fdmgroup.bookstore.data.OrderRepository;
import com.fdmgroup.bookstore.data.UserRepository;
import com.fdmgroup.bookstore.model.Book;
import com.fdmgroup.bookstore.model.BookGenre;
import com.fdmgroup.bookstore.model.Order;
import com.fdmgroup.bookstore.model.User;
import com.fdmgroup.bookstore.service.AuthenticationService;
import com.fdmgroup.bookstore.service.BookService;
import com.fdmgroup.bookstore.service.OrderingService;

import Exception.ItemNotFoundException;
import Exception.UserNotFoundException;

class AllTest {

	public Book book1 = new Book(1, 250.0, "mybook", "rohit singh", BookGenre.ACTION);
	public Book book2 = new Book(2, 251.0, "mybook1", "barak obama", BookGenre.CRIME);
	public Book book3 = new Book(3, 252.0, "mybook2", "jamil khan", BookGenre.THRILLER);
	public Book book4 = new Book(4, 252.0, "mybook", "barak obama", BookGenre.THRILLER);

	public List<Book> booklist = new ArrayList<Book>();
	public User user1 = new User(1, "first", "last", "firstlast", "mypass", "myemail", new ArrayList<Order>());
	public User user2 = new User(2, "first1", "last1", "firstlast1", "mypass1", "myemail1", new ArrayList<Order>());
	public List<Order> orderlist = new ArrayList<Order>();

	@Test

	public void authenticateSuccessTest() throws UserNotFoundException {

		UserRepository mockuserRepository = mock(UserRepository.class);
		AuthenticationService authenticationService = new AuthenticationService(mockuserRepository);

		when(mockuserRepository.validate(user1.getUsername(), user1.getPassword())).thenReturn(true);
		when(mockuserRepository.findByUsername(user1.getUsername())).thenReturn(user1);

		User returnedUser;
		returnedUser = authenticationService.authenticate(user1.getUsername(), user1.getPassword());

		assertEquals(returnedUser, user1);
		verify(mockuserRepository, times(1)).findByUsername(user1.getUsername());
		verify(mockuserRepository, times(1)).validate(user1.getUsername(), user1.getPassword());

	}

	@Test
	public void authenticateFailTest() throws UserNotFoundException {

		UserRepository mockuserRepository = mock(UserRepository.class);
		AuthenticationService authenticationService = new AuthenticationService(mockuserRepository);

		when(mockuserRepository.validate("nouser", "nopass")).thenReturn(false);
		when(mockuserRepository.findByUsername("nouser")).thenReturn(null);

		try {

			User returnedUser = authenticationService.authenticate("nouser", "nopass");

		} catch (UserNotFoundException e) {

			e.getMessage();
		}

		verify(mockuserRepository, times(1)).validate("nouser", "nopass");

	}

	@Test
	public void userFindByIdTestSuccess() {
		UserRepository mockuserRepository = mock(UserRepository.class);
		AuthenticationService authenticationService = new AuthenticationService(mockuserRepository);
		when(mockuserRepository.findById(1)).thenReturn(user1);

		Object result = mockuserRepository.findById(1);
		assertEquals(result, user1);

	}

	@Test
	public void userFindByIdTestfail() {
		UserRepository mockuserRepository = mock(UserRepository.class);
		AuthenticationService authenticationService = new AuthenticationService(mockuserRepository);

		when(mockuserRepository.findById(5)).thenReturn(null);
		when(mockuserRepository.findById(1)).thenReturn(user1);

		try {
			User result = authenticationService.findById(1);

		} catch (UserNotFoundException e) {
			e.getMessage();
		}
		try {

			User result = authenticationService.findById(5);

		} catch (UserNotFoundException e) {
			e.getMessage();
		}

		verify(mockuserRepository, times(1)).findById(5);
		verify(mockuserRepository, times(1)).findById(1);

	}

	@Test
	public void findAllBookTest() {
		BookRepository mockbookRepository = mock(BookRepository.class);
		BookService bookService = new BookService(mockbookRepository);

		ArrayList listresult = mock(ArrayList.class);

		when(mockbookRepository.findAll()).thenReturn(listresult);

		assertEquals(listresult, mockbookRepository.findAll());
		verify(mockbookRepository, times(1)).findAll();

	}

	@Test
	public void findBookByGenreTest() {
		booklist.add(book1);
		booklist.add(book2);
		booklist.add(book3);
		booklist.add(book4);
		
		BookRepository mockbookRepository = mock(BookRepository.class);
		BookService bookService = new BookService(mockbookRepository);

		when(mockbookRepository.findAll()).thenReturn(booklist);
		List<Book> result = bookService.getBooksOfGenre(BookGenre.THRILLER);

		List<Book> actualResult = new ArrayList<Book>();

		actualResult.add(booklist.get(2));
		actualResult.add(booklist.get(3));
		assertEquals(result, actualResult);

	}

	@Test
	public void findBookByTitleTest() {
		booklist.add(book1);
		booklist.add(book2);
		booklist.add(book3);
		booklist.add(book4);

		BookRepository mockbookRepository = mock(BookRepository.class);
		BookService bookService = new BookService(mockbookRepository);
		
		when(mockbookRepository.findAll()).thenReturn(booklist);
		List<Book> result = bookService.searchBooksByTitle("mybook");

		List<Book> actualResult = new ArrayList<Book>();

		actualResult.add(booklist.get(0));
		actualResult.add(booklist.get(3));
		assertEquals(actualResult, result);

	}

	@Test
	public void findBookByAuthorNameTest() {
		booklist.add(book1);
		booklist.add(book2);
		booklist.add(book3);
		booklist.add(book4);

		BookRepository mockbookRepository = mock(BookRepository.class);
		BookService bookService = new BookService(mockbookRepository);

		when(mockbookRepository.findAll()).thenReturn(booklist);

		List<Book> result = bookService.searchBooksByAuthorName("barak");

		List<Book> actualResult = new ArrayList<Book>();

		actualResult.add(booklist.get(1));
		actualResult.add(booklist.get(3));

		assertEquals(actualResult, result);
	}

	@Test
	public void findBookByIdSuceess() {
		booklist.add(book1);
		booklist.add(book2);
		booklist.add(book3);

		BookRepository mockbookRepository = mock(BookRepository.class);
		BookService bookService = new BookService(mockbookRepository);

		when(mockbookRepository.findById(1)).thenReturn(booklist.get(0));

		Book result = mockbookRepository.findById(1);

		assertEquals(result, booklist.get(0));
	}

	@Test
	public void findBookByIdFail() {
		booklist.add(book1);
		booklist.add(book2);
		booklist.add(book3);

		BookRepository mockbookRepository = mock(BookRepository.class);
		BookService bookService = new BookService(mockbookRepository);

		when(mockbookRepository.findById(10)).thenReturn(null);

		try {
			Book result = bookService.findById(10);

		} catch (ItemNotFoundException e) {
			e.getMessage();
		}
		verify(mockbookRepository, times(1)).findById(10);

	}

	@Test
	public void placeOrderTest() throws ItemNotFoundException, UserNotFoundException {

		booklist.add(book1);
		booklist.add(book2);
		booklist.add(book3);

		OrderRepository mockorderRepository = (mock(OrderRepository.class));
		OrderingService orderingService = new OrderingService(mockorderRepository);

		BookRepository mockbookRepository = mock(BookRepository.class);
		BookService bookService = new BookService(mockbookRepository);

		UserRepository mockuserRepository = mock(UserRepository.class);
		AuthenticationService authenticationService = new AuthenticationService(mockuserRepository);

		when(mockbookRepository.findById(1)).thenReturn(booklist.get(0));
		bookService.findById(1);

		when(mockuserRepository.findById(1)).thenReturn(user1);
		authenticationService.findById(1);

		Order order1 = new Order(book1.getItemId(), book1, user1, LocalDateTime.MAX);
		when(mockorderRepository.save(order1)).thenReturn(order1);

		Order result = orderingService.placeOrder(book1, user1);

		assertEquals(result, order1);

	}

	@Test
	public void placeOrdersTest() throws ItemNotFoundException, UserNotFoundException {

		booklist.add(book1);
		booklist.add(book2);

		OrderRepository mockorderRepository = (mock(OrderRepository.class));
		OrderingService orderingService = new OrderingService(mockorderRepository);

		BookRepository mockbookRepository = mock(BookRepository.class);
		BookService bookService = new BookService(mockbookRepository);

		UserRepository mockuserRepository = mock(UserRepository.class);
		AuthenticationService authenticationService = new AuthenticationService(mockuserRepository);

		when(mockbookRepository.findById(1)).thenReturn(booklist.get(0));
		when(mockbookRepository.findById(2)).thenReturn(booklist.get(1));
		bookService.findById(1);
		bookService.findById(2);

		when(mockuserRepository.findById(1)).thenReturn(user1);
		authenticationService.findById(1);

		Order order1 = new Order(book1.getItemId(), book1, user1, LocalDateTime.MAX);
		Order order2 = new Order(book2.getItemId(), book2, user1, LocalDateTime.MAX);

		when(orderingService.placeOrder(book1, user1)).thenReturn(order1);
		when(orderingService.placeOrder(book2, user1)).thenReturn(order2);

		List<Order> result = orderingService.placeOrders(booklist, user1);

		List<Order> actualresult = new ArrayList<Order>();
		actualresult.add(order1);
		actualresult.add(order2);

		assertEquals(result, actualresult);

	}

	@Test
	public void getOrdersForUserTest() {

		booklist.add(book1);
		booklist.add(book2);

		OrderRepository mockorderRepository = (mock(OrderRepository.class));
		OrderingService orderingService = new OrderingService(mockorderRepository);

		Order order1 = new Order(book1.getItemId(), book1, user1, LocalDateTime.MAX);
		Order order2 = new Order(book2.getItemId(), book2, user1, LocalDateTime.MAX);

		when(orderingService.placeOrder(book1, user1)).thenReturn(order1);
		when(orderingService.placeOrder(book2, user1)).thenReturn(order2);

		orderlist = orderingService.placeOrders(booklist, user1);

		when(mockorderRepository.findAll()).thenReturn(orderlist);

		List<Order> result = orderingService.getOrdersForUser(user1);
		List<Order> expectedList = new ArrayList<Order>();
		expectedList.add(order1);
		expectedList.add(order2);
		assertEquals(result, expectedList);

	}

	@Test
	public void getOrdersForBookTest() {
		booklist.add(book1);
		booklist.add(book2);

		OrderRepository mockorderRepository = (mock(OrderRepository.class));
		OrderingService orderingService = new OrderingService(mockorderRepository);

		Order order1 = new Order(book1.getItemId(), book1, user1, LocalDateTime.MAX);
		Order order2 = new Order(book2.getItemId(), book2, user1, LocalDateTime.MAX);

		when(orderingService.placeOrder(book1, user1)).thenReturn(order1);
		when(orderingService.placeOrder(book2, user1)).thenReturn(order2);

		orderlist = orderingService.placeOrders(booklist, user1);

		when(mockorderRepository.findAll()).thenReturn(orderlist);

		List<Order> result = orderingService.getOrdersForBook(book1);

		List<Order> expectedList = new ArrayList<Order>();
		expectedList.add(order1);
		assertEquals(result, expectedList);
	}

}
