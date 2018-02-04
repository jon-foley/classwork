package entity;

import entity.CustomerOrder;
import entity.OrderedBookPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-12T23:33:19")
@StaticMetamodel(OrderedBook.class)
public class OrderedBook_ { 

    public static volatile SingularAttribute<OrderedBook, Short> quantity;
    public static volatile SingularAttribute<OrderedBook, CustomerOrder> customerOrder;
    public static volatile SingularAttribute<OrderedBook, OrderedBookPK> orderedBookPK;

}