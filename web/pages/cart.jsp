<section id="block_cart">
    <h1>Your cart</h1>
    <%

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
    %>

    <h3>You are not logged in</h3>
    <h3>Please log in at "user" menu tab</h3>
    <%
    } else {
        String message = (String) session.getAttribute("message");
        if (message != null) //Eliminamos el mensaje consumido
        {
            session.removeAttribute("message");
    %>
    <p id="cartMessage"><%=message%></p>
    <%
        }
    %>
    <ul id="cartList">
    </ul>
    <form method="post" action="BuyProducts">
        <input id="cartInput" type="hidden" name="cart" value="">
        <input id="cartBuy" type="submit" value="Buy all for: "><span id="cartTotalAmount"></span>
    </form>
    <%
        }
    %>

</section>
<%
    if (userId != null) {
%>
<script src="js/cart.js"></script>
<%
    }
%>