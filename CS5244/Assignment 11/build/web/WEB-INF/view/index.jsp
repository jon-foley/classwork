<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
    Document   : index
    Created on : Sep 19, 2016, 5:58:02 PM
    Author     : jfoley
--%>

            <div id="welcomeBody" class="body-content-container">
                <div id="welcomeMessageContainer" class="body-top-container">
                    <h2 id="welcomeMessageText">Welcome to The Book Depot!</h2>
                </div>
                <div id="columnContainer" class="body-column-container">
                    <div id="sampleProductsColumnContainer" class="body-right-column-container">
                        <div id="sampleProductsHeadingContainer" class="body-right-column-header-container">
                            <h3 id="sampleProductsHeadingText">Popular Now</h3>
                        </div>
                        <div id="sampleProductsGridContainer" class="body-right-column-grid-container">
                            <c:forEach var="sampleBook" items="${randomBooks}" varStatus="iter" end="3">
                                <div class="productContainer">
                                    <div class="productContainer-image">
                                        <img src="${initParam.bookImagePath}${sampleBook.title}.gif" alt="${sampleBook.title}">
                                    </div>
                                    <div class="productContainer-nameLink">
                                        <a class="truncatedLink" href="#">${sampleBook.title}</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            
