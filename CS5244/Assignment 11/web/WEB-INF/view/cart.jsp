<%-- 
    Document   : cart
    Created on : Oct 30, 2016, 10:27:32 PM
    Author     : jfoley
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="body-content-container">
    <c:set var="numberOfCartItems" value="${(empty cart) ? 0 : cart.numberOfItems}" />
    <div class="body-top-container">
        <h2>Your shopping cart contains ${numberOfCartItems} items</h2>
    </div>
    <div id= "cart-container">
         <c:choose>
            <c:when test="${numberOfCartItems > 0}">
                <p>
                    <b>Subtotal:</b> <fmt:formatNumber value="${cart.subtotal}" type="currency" />
                </p>
                <div>
                    <a id="checkoutButton" href="checkout">
                        <button>Proceed to Checkout</button>
                    </a>
                    <a id="continueShoppingButton" href="category">
                        <button>Continue Shopping</button>
                    </a>
                </div>
                    <table id="cartTable">
                        <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">
                            <c:set var="book" value="${cartItem.book}"/>
                            <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                                <td>${book.title}</td>
                                <td>
                                    ${book.price}
                                    <c:set var="quantity" value="${cartItem.quantity}" />
                                    <c:choose>
                                        <c:when test="${quantity > 1}">
                                            (x${quantity})
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td class="quantityImageCells">
                                    <form class="cartForms" name="increment" action="increment" method="post">
                                        <input type="hidden" name="bookId" value="${book.id}" />
                                        <input id="incrementButton" type="image" src="${initParam.imagePath}plus.gif" alt="Increment" />
                                    </form>
                                </td>
                                <td class="quantityImageCells">
                                    <form class="cartForms" name="decrement" action="decrement" method="post">
                                        <input type="hidden" name="bookId" value="${book.id}" />
                                        <input id="decrementButton" type="image" src="${initParam.imagePath}minus.gif" alt="Decrement" />
                                    </form>
                                </td>
                            </tr>

                        </c:forEach>
                    </table>
            </c:when>
            <c:otherwise>
                <div>
                    <p>Your shopping cart is empty!</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
 
