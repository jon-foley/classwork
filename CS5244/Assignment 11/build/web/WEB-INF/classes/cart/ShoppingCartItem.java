/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cart;

import entity.Book;

/**
 *
 * @author jfoley
 */
public class ShoppingCartItem {

    Book book;
    short quantity;

    public ShoppingCartItem(Book book) {
        this.book = book;
        quantity = 1;
    }

    public Book getBook() {
        return book;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public double getTotal() {
        double amount = 0;
        amount = (this.getQuantity() * book.getPrice().doubleValue());
        return amount;
    }

    public Object getProduct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}