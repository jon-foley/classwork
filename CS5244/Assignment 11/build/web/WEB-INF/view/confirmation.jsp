<%-- 
    Document   : confirmation
    Created on : Nov 27, 2016, 8:25:23 PM
    Author     : jfoley
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="body-content-container">
    <div id="confirmationText">
        Your confirmation number is:
        <strong>${orderRecord.confirmationNumber}</strong>
    </div>

    <div id="orderSummaryTableContainer" >

        <table id="orderSummaryTable">
            <tr>
                <th colspan="3">Order Summary</th>
            </tr>

            <tr>
                <td>Book</td>
                <td>Quantity</td>
                <td>Price</td>
            </tr>

            <c:forEach var="orderedBook" items="${orderedBooks}" varStatus="iter">

                <tr class="${((iter.index % 2) != 0) ? 'lightBlue' : 'white'}">
                    <td>${books[iter.index].title}</td>
                    <td class="quantityColumn">
                        ${orderedBook.quantity}
                    </td>
                    <td class="confirmationPriceColumn">
                        <fmt:formatNumber value="${books[iter.index].price * orderedBook.quantity}" type="currency" />
                    </td>
                </tr>

            </c:forEach>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="2" id="deliverySurchargeCellLeft"><strong>delivery surcharge:</strong></td>
                <td id="deliverySurchargeCellRight"><fmt:formatNumber value="${initParam.shippingCost}" type="currency" /></td>
            </tr>

            <tr class="lightBlue">
                <td colspan="2" id="totalCellLeft"><strong>total:</strong></td>
                <td id="totalCellRight"><fmt:formatNumber value="${orderRecord.amount}" type="currency" /></td>
            </tr>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="3" id="dateProcessedRow"><strong>date processed:</strong>
                    ${orderRecord.dateCreated}
                </td>
            </tr>
          
            <tr>
                <td colspan="3" class="lightBlue">
                    <hr>
                    <strong>name:</strong> ${customer.name}
                    <br>
                    <strong>address:</strong> ${customer.address}
                    <br>
                    <strong>email:</strong> ${customer.email}
                    <br>
                    <strong>phone:</strong> ${customer.phone}
                    <br>
                    <strong>credit card:</strong> ${customer.last4CcDigits}
                    <br>
                    <strong>exp. date:</strong> <fmt:formatDate value="${customer.ccExpDate}" pattern="MM-yyyy"/>
                </td>
            </tr>
        </table>
    </div>
</div>
