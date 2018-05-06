<section id="block_cart">
    <h1>Your cart</h1>
    <%

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
    %>

    <h3 class="bigError">You are not logged in</h3>
    <h3 class="bigError">Please log in at "user" menu tab</h3>
    <%
    } else {
        String message = (String) session.getAttribute("message");
        if (message != null) //Eliminamos el mensaje consumido
        {
            session.removeAttribute("message");
    %>
    <h3><%=message%></h3>
    <%
        }
    %>
    <h3 id="cartTotalAmount">
        <input id="cartBuy" type="submit" value="Buy" form="buyProductsForm"> 
        all for : 
        <span></span>
    </h3>
    <h3 id="epmptyCart">Empty</h3>
    <form method="post" action="BuyProducts" id="buyProductsForm">
        <input id="cartInput" type="hidden" name="cart" value="">
    </form>
    <ul id="cartList">
    </ul>
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