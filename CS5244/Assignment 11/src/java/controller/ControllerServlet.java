/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.ShoppingCart;
import entity.Book;
import entity.Category;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.CategoryFacade;
import session.OrderManager;
import validate.Validator;

/**
 *
 * @author jfoley
 */
@WebServlet(name = "ControllerServlet", loadOnStartup=1, urlPatterns = {"/index", "/addToCart", "/category", "/product", "/checkout", "/viewCart", "/updateCart", "/increment", "/decrement", "/purchase", "/chooseLanguage", "/product", "/confirmation"})
public class ControllerServlet extends HttpServlet {
    
    @EJB
    private CategoryFacade categoryFacade;
    
    @EJB
    private BookFacade bookFacade;
    
    @EJB
    private OrderManager orderManager;
    
    private String shippingCost;
    
    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute("categories", categoryFacade.findAll());
        shippingCost = getServletContext().getInitParameter("shippingCost");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      String userPath = request.getServletPath();
      System.out.println("Controller servlet initial userpath (GET) " + userPath);
      HttpSession session = request.getSession();
      ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
      
        if (userPath.equals("/index")){
            //Select a random category for the books on the index page
           int randomId = new Random().nextInt(4) + 1;
           Category randomCategory = categoryFacade.find(randomId);
           Collection<Book> randomBooks = randomCategory.getBookCollection();
           request.setAttribute("randomBooks", randomBooks);
           request.setAttribute("randomCategory", randomCategory);
        }
      
        // if category page is requested
        else if (userPath.equals("/category")) {
            // get categoryId from request
            String categoryId = request.getQueryString();

            if (categoryId != null) {

                // get selected category
                Category selectedCategory = categoryFacade.find(Integer.parseInt(categoryId));

                // place selected category in session scope
                session.setAttribute("selectedCategory", selectedCategory);

                // get all books for selected category
                Collection<Book> categoryProducts = selectedCategory.getBookCollection();

                // place category books in session scope
                session.setAttribute("categoryProducts", categoryProducts);

            }
        // if cart page is requested
        } else if (userPath.equals("/viewCart")) {
            // TODO: Implement cart page request

            userPath = "/cart";

        // if checkout page is requested
        } else if (userPath.equals("/checkout")) {
            //Calculate the total cost for items in the cart with shipping
            if (cart != null){
                cart.calculateTotal(shippingCost);
            }

        // if user switches language
        } else if (userPath.equals("/chooseLanguage")) {
            // TODO: Implement language request

        }
        else if (userPath.equals("/product")){
            //TODO implement product page request
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";
        System.out.println("Controller servlet final URL (GET) " + url);
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();
        System.out.println("Controller servlet initial userpath (POST) " + userPath);
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        Validator validator = new Validator();

        // if addToCart action is called
        if (userPath.equals("/addToCart")) {
           
            if (cart == null){
               cart = new ShoppingCart();
               session.setAttribute("cart", cart);
            }
            
            //get the book by id
            String bookId = request.getParameter("bookId");
            if (bookId != null){
                Book book = bookFacade.find(Integer.parseInt(bookId));
                cart.addItem(book);
            }
            
            //get category page
            userPath = "/category";

        // if updateCart action is called
        } else if (userPath.equals("/updateCart")) {
            // TODO: Implement update cart action
          
        //if increment action called
        } else if (userPath.equals("/increment")){
            String bookId = request.getParameter("bookId");
            Book book = bookFacade.find(Integer.parseInt(bookId));
            cart.increment(book);
            userPath = "/cart";
          
        //if decrement actino called
        } else if (userPath.equals("/decrement")){
            String bookId = request.getParameter("bookId");
            Book book = bookFacade.find(Integer.parseInt(bookId));
            cart.decrement(book);
            userPath = "/cart";
        }
        // if purchase action is called
        else if (userPath.equals("/purchase")) {
//            if (cart != null){} TODO
            // validate user data
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String ccNumber = request.getParameter("creditcard");
            String expirationMonth = request.getParameter("expirationDate");
            String expirationYear = request.getParameter("expirationYear");
            boolean validationErrorFlag = false;
            validationErrorFlag = validator.validateForm(name, email, phone, address, ccNumber,expirationMonth              , expirationYear, request);
            
            // if validation error found, return user to checkout
            if (validationErrorFlag == true) {
                request.setAttribute("validationErrorFlag", validationErrorFlag);
                userPath = "/checkout";
            } else {
                int orderId = orderManager.placeOrder(name, email, phone, address, ccNumber, expirationMonth, expirationYear, cart);

                // if order processed successfully send user to confirmation page
                if (orderId != 0) {

                    // dissociate shopping cart from session
                    cart = null;

                    // end session
                    session.invalidate();

                    // get order details
                    Map<String, Object> orderMap = orderManager.getOrderDetails(orderId);

                    // place order details in request scope
                    request.setAttribute("customer", orderMap.get("customer"));
                    request.setAttribute("books", orderMap.get("books"));
                    request.setAttribute("orderRecord", orderMap.get("orderRecord"));
                    request.setAttribute("orderedBooks", orderMap.get("orderedBooks"));

                    userPath = "/confirmation";

                // otherwise, send back to checkout page and display error
                } else {
                    userPath = "/checkout";
                    request.setAttribute("orderFailureFlag", true);
                }
            }           
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";
        System.out.println("Controller servlet final URL (POST) " + url);
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
