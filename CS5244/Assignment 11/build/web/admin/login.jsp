<%-- 
    Document   : login
    Created on : Dec 10, 2016, 4:51:14 PM
    Author     : jfoley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="adminContainer">
    <form action="j_security_check" method=post>
        <div id="loginBox">
            <p><strong>username:</strong>
                <input type="text" size="20" name="j_username"></p>

            <p><strong>password:</strong>
                <input type="password" size="20" name="j_password"></p>

            <p><input type="submit" value="submit"></p>
        </div>
    </form>
</div>
