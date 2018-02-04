/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jfoley
 */
@Entity
@Table(name = "ordered_book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderedBook.findAll", query = "SELECT o FROM OrderedBook o"),
    @NamedQuery(name = "OrderedBook.findByCustomerOrderId", query = "SELECT o FROM OrderedBook o WHERE o.orderedBookPK.customerOrderId = :customerOrderId"),
    @NamedQuery(name = "OrderedBook.findByBookId", query = "SELECT o FROM OrderedBook o WHERE o.orderedBookPK.bookId = :bookId"),
    @NamedQuery(name = "OrderedBook.findByQuantity", query = "SELECT o FROM OrderedBook o WHERE o.quantity = :quantity")})
public class OrderedBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderedBookPK orderedBookPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private short quantity;
    @JoinColumn(name = "customer_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CustomerOrder customerOrder;

    public OrderedBook() {
    }

    public OrderedBook(OrderedBookPK orderedBookPK) {
        this.orderedBookPK = orderedBookPK;
    }

    public OrderedBook(OrderedBookPK orderedBookPK, short quantity) {
        this.orderedBookPK = orderedBookPK;
        this.quantity = quantity;
    }

    public OrderedBook(int customerOrderId, int bookId) {
        this.orderedBookPK = new OrderedBookPK(customerOrderId, bookId);
    }

    public OrderedBookPK getOrderedBookPK() {
        return orderedBookPK;
    }

    public void setOrderedBookPK(OrderedBookPK orderedBookPK) {
        this.orderedBookPK = orderedBookPK;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderedBookPK != null ? orderedBookPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderedBook)) {
            return false;
        }
        OrderedBook other = (OrderedBook) object;
        if ((this.orderedBookPK == null && other.orderedBookPK != null) || (this.orderedBookPK != null && !this.orderedBookPK.equals(other.orderedBookPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderedBook[ orderedBookPK=" + orderedBookPK + " ]";
    }
    
}
