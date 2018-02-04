/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.OrderedBook;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jfoley
 */
@Stateless
public class OrderedBookFacade extends AbstractFacade<OrderedBook> {

    @PersistenceContext(unitName = "Assignment_10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderedBookFacade() {
        super(OrderedBook.class);
    }
    
    // manually created
    public List<OrderedBook> findByOrderId(Object id) {
        return em.createNamedQuery("OrderedBook.findByCustomerOrderId")
            .setParameter("customerOrderId", id)
            .getResultList();
    }
    
}
