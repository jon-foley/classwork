/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Book;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedBook;
import entity.OrderedBookPK;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Manages orders of books
 * 
 * @author jfoley
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderManager {
    
    @Resource
    private SessionContext context;
    
    @PersistenceContext(unitName = "Assignment_10PU")
    private EntityManager em;
    
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    
    @EJB
    private OrderedBookFacade orderedBookFacade;
    
    @EJB
    private BookFacade bookFacade;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String name, String email, String phone, String address, String ccNumber, String expirationMonth, String expirationYear, ShoppingCart cart) {
        
        try {
            Customer customer = addCustomer(name, email, phone, address, expirationMonth, expirationYear, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getId();
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {
        em.flush();
        
        List<ShoppingCartItem> items = cart.getItems();

        // iterate through shopping cart and create OrderedBooks
        for (ShoppingCartItem scItem : items) {

            int bookId = scItem.getBook().getId();

            // set up primary key object
            OrderedBookPK orderedBookPK = new OrderedBookPK();
            orderedBookPK.setCustomerOrderId(order.getId());
            orderedBookPK.setBookId(bookId);

            // create ordered item using PK object
            OrderedBook orderedItem = new OrderedBook(orderedBookPK);

            // set quantity
            orderedItem.setQuantity(scItem.getQuantity());
            em.persist(orderedItem);
        }
    }

    private Customer addCustomer(String name, String email, String phone, String address, String expirationMonth, String expirationYear, String ccNumber) throws ParseException {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        Date expDate = null;
        expDate = new SimpleDateFormat("yyyy-MM-dd").parse(expirationYear + (expirationMonth.length() == 1 ? "-0" : "-") + expirationMonth + "-01");
        customer.setCcExpDate(expDate);
        customer.setCcNumber(ccNumber);
        em.persist(customer);
        return customer;
    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {
        // set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);
        
        em.persist(order);
        return order;
    }
    
    public Map<String, Object> getOrderDetails(int orderId){
        Map<String, Object> orderMap = new HashMap<>(4);
        
        CustomerOrder order = customerOrderFacade.find(orderId);
        Customer customer = order.getCustomer();
        List<OrderedBook> orderedBooks = orderedBookFacade.findByOrderId(orderId);
        List<Book> books = new ArrayList<>();
        for ( OrderedBook ob : orderedBooks){
            Book b = (Book) bookFacade.find(ob.getOrderedBookPK().getBookId());
            books.add(b);
        }
        orderMap.put("orderRecord", order);
        orderMap.put("customer", customer);
        orderMap.put("orderedBooks", orderedBooks);
        orderMap.put("books", books);
        
        return orderMap;
    }
}
