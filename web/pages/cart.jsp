<section id="block_cart">
    <%
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
    %>

    <h1>Your cart</h1>
    <h3>You are not logged in</h3>
    <h3>Please log in at next menu tab</h3>
    <%
    } else {
    %>
    <h1>Your cart</h1>
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