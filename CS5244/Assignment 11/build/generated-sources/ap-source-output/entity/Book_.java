package entity;

import entity.Category;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-12T23:33:19")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile SingularAttribute<Book, String> author;
    public static volatile SingularAttribute<Book, BigDecimal> price;
    public static volatile SingularAttribute<Book, Boolean> isPublic;
    public static volatile SingularAttribute<Book, Integer> id;
    public static volatile SingularAttribute<Book, String> title;
    public static volatile SingularAttribute<Book, Category> category;

}