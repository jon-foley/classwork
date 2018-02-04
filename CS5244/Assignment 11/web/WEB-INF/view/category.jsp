<%-- 
    Document   : categories
    Created on : Sep 20, 2016, 8:46:04 PM
    Author     : jfoley
--%>


<div id="categoriesBody" class="body-content-container">
    <div id="columnContainer" class="category-column-container">
        <div id="categoriesColumnContainer" class="category-left-column-container">
            <div id="categoriesHeaderContainer">
                <h3 id="categoryButtonsHeaderText">Categories</h3>
            </div>
            <div id="categoriesLinksContainer" >
                <c:forEach var="category" items="${categories}">
                    <div>
                        <a href="category?${category.id}">
                            <c:choose>
                                <c:when test="${selectedCategory.id == category.id}">
                                    <button class="category-button-selected">${selectedCategory.name}</button>
                                </c:when>
                                <c:otherwise>  
                                    <button class="category-button">${category.name}</button>
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div id="productsColumnContainer" class="category-right-column-container">
            <div id="productsHeadingContainer" class="body-right-column-header-container">
                <h3 id="productsHeadingText">${selectedCategory.name}</h3>
            </div>
            <div id="productsTableContainer" class="category-right-column-grid-container">
                <c:forEach var="book" items="${categoryProducts}" varStatus="iter">
                    <div class="bookContainer">
                        <div class="thumbnail-container">
                            <img src="${initParam.bookImagePath}${book.title}.gif" alt="${book.title}" class="thumbnail">
                        </div>
                        ${book.title}<br>
                        by ${book.author}<br>
                        $${book.price}<br>
                        <div class="clear-left">
                            <div class="read-now-button-container">
                                <form name="addToCart" action="addToCart" method="POST">
                                    <input type="hidden" name="bookId" value="${book.id}" />
                                    <input class="addToCartButton" type="submit" value="Add To Cart" name="submit" />
                                </form>
                                <div>
                                    <c:choose>
                                        <c:when test="${book.isPublic == true}">
                                            <button class="read-now-button">
                                                Read Now
                                            </button>
                                        </c:when>
                                        <c:otherwise>

                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
