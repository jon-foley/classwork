/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jfoley
 */
@Embeddable
public class OrderedBookPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "customer_order_id")
    private int customerOrderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "book_id")
    private int bookId;

    public OrderedBookPK() {
    }

    public OrderedBookPK(int customerOrderId, int bookId) {
        this.customerOrderId = customerOrderId;
        this.bookId = bookId;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) customerOrderId;
        hash += (int) bookId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderedBookPK)) {
            return false;
        }
        OrderedBookPK other = (OrderedBookPK) object;
        if (this.customerOrderId != other.customerOrderId) {
            return false;
        }
        if (this.bookId != other.bookId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderedBookPK[ customerOrderId=" + customerOrderId + ", bookId=" + bookId + " ]";
    }
    
}
