<%-- 
    Document   : checkout
    Created on : Oct 31, 2016, 1:07:35 AM
    Author     : jfoley
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="body-content-container">
    <script src="js/jquery.validate.js" type="text/javascript"></script>
    <script src="js/additional-methods.js" type="text/javascript"></script>
    <script type="text/javascript">
     
        $(document).ready(function(){
            $("#purchaseForm").validate({
                rules: {
                    name: "required",
                    email: {
                        required: false,
                        email: true
                    },
                    phone: {
                        required: true,
                        number: true,
                        minlength: 9,
                        phoneUS: true
                    },
                    address: {
                        required: true
                    },
                    creditcard: {
                        required: true,
                        creditcard: true
                    },
                    expirationDate: {
                        required: true,
                        number: true
                    },
                    expirationYear: {
                        required: true,
                        date: true
                    }
                    
                }
            });
        });
    </script>
    <div class="body-top-container">
        <h2>Checkout</h2>
    </div>
    <div id="paymentContentsContainer">
        <div id="paymentContainer">
            <form id="purchaseForm" action="purchase" name="purchase" method="post">
                <table>
                <c:if test="${!empty validationErrorFlag}">
                    <tr>
                        <td colspan="2" style="text-align:left">
                            <span class="error smallText">Please provide valid entries for the following field(s):

                                <c:if test="${!empty nameError}">
                                  <br><span class="indent"><strong>name</strong> (e.g., Bilbo Baggins)</span>
                                </c:if>
                                <c:if test="${!empty emailError}">
                                  <br><span class="indent"><strong>email</strong> (e.g., b.baggins@hobbit.com)</span>
                                </c:if>
                                <c:if test="${!empty phoneError}">
                                  <br><span class="indent"><strong>phone</strong> (e.g., 222333444)</span>
                                </c:if>
                                <c:if test="${!empty addressError}">
                                  <br><span class="indent"><strong>address</strong> (e.g., Korunní 56)</span>
                                </c:if>
                                <c:if test="${!empty ccNumberError}">
                                  <br><span class="indent"><strong>credit card</strong> (e.g., 1111222233334444)</span>
                                </c:if>
                                <c:if test="${!empty expirationMonthError}">
                                  <br><span class="indent"><strong>expiration month</strong> (e.g., 2)</span>
                                </c:if>
                                <c:if test="${!empty expirationYearError}">
                                    <br><span class="indent"><strong>expiration year</strong> (e.g., 2020)</span>
                                </c:if>

                            </span>
                        </td>
                    </tr>
                 </c:if>
                <tr>
                    <td>Full Name:</td>
                    <td><input id="fullName" name="name" type="text" size="31" maxlength="45" value="${param.name}"></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input id="email" name="email" type="text" size="31" value="${param.email}"></td>
                </tr>
                <tr>
                    <td>Phone:</td>
                    <td><input id="phone" name="phone" type="text" size="31" maxlength="45" value="${param.phone}"></td>
                </tr>
                <tr>
                    <td>Address:</td>
                    <td><input id="address" name="address" type="text" size="31" maxlength="45" value="${param.address}"></td>
                </tr>
                <tr>
                    <td>Credit Card:</td>
                    <td><input id="creditCard" name="creditcard" type="text" size="31" maxlength="45" value="${param.creditcard}"></td>
                </tr>
                <tr>
                    <td>Expiration Date:</td>
                    <td>
                        <table>
                            <tr>
                                <td>
                                    <select name="expirationDate">
                                            <option value="Month" selected>Month</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                            <option value="6">6</option>
                                            <option value="7">7</option>
                                            <option value="8">8</option>
                                            <option value="9">9</option>
                                            <option value="10">10</option>
                                            <option value="11">11</option>
                                            <option value="12">12</option>
                                    </select>
                                </td>
                                <td>
                                    <select name="expirationYear">
                                        <option value="Year" selected>Year</option>
                                        <option value="2016">2016</option>
                                        <option value="2017">2017</option>
                                        <option value="2018">2018</option>
                                        <option value="2019">2019</option>
                                        <option value="2020">2020</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </table>
                <div id="purchaseSubmitDiv">
                    <input type="submit" value="Submit" name="submit" />
                </div>
            </form>
        </div>
        <div id="summaryContainer">
            <div id="summaryDiv">
                <div>
                    Your credit card will be charged <b><fmt:formatNumber value="${cart.total}" type="currency" /></b>
                    <br/>
                    (<fmt:formatNumber value="${cart.subtotal}" type="currency" /> + <fmt:formatNumber value="${initParam.shippingCost}" type="currency" /> shipping)
                </div>
            </div>
        </div>
    </div>
</div>
 

