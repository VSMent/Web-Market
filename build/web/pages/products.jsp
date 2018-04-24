<%@page import="java.sql.ResultSet"%>
<%@page import="p2.DBAccess"%>
<%@page import="java.util.ArrayList"%>
<section id="block_products">
    <h1>Products</h1>
    <p id="message"></p>
    <table id="products">
        <caption>Our products list</caption>
        <!-- Headers -->
        <tr>
            <th>Product</th>
            <th>Name</th>
            <th>In pack</th>
            <th>Price per pack</th>
            <th>Amount</th>
            <th>Cart</th>
        </tr>
        <%
            DBAccess con = new DBAccess();
            ResultSet products = con.getProductsSet();
        %>

        <!-- Products -->

        <%
            int rowCount = 0;
            while (products.next()) {
                if (rowCount == 3) {
                    rowCount = 0;
                }
//                String id = products.getString("id");
//                int stock = products.getInt("stock");
                String name = products.getString("name");
                String description = products.getString("description");
                String image = products.getString("image");

                float price = products.getFloat("price");
                int in_pack = products.getInt("in_pack");
        %>

        <%if (rowCount == 0) {%>
        <tr>
            <td rowspan="3"><img class="productPic" src="<%=image%>" alt=""></td>
            <td rowspan="3"><p class="productName"><%=name%></p><hr><p><%=description%></p></td>
            <td><p><%=in_pack%></p></td>
            <td><p><%=price%></p></td>
            <td><input min="0" value="0" class="productInput" type="number"></td>
            <td><button class="addToCart">Add</button></td>
        </tr>
        <%} else {%>
        <tr>
            <td><p><%=in_pack%></p></td>
            <td><p><%=price%></p></td>
            <td><input min="0" value="0" class="productInput" type="number"></td>
            <td><button class="addToCart">Add</button></td>
        </tr>

        <%}%>

        <%
                rowCount++;
            }
        %>



        <!-- Footer -->
        <tr>
            <td colspan="6">
                <!--<button style="float: left; margin: 1vmin 0 0 1vmin;">Perv page</button>-->
                <p style="font-weight: bold; text-align: center; display: inline;">Total price: <span id="productTotalAmount">0$</span></p>
                <!--<button style="float: right; margin: 1vmin 0 0 1vmin;">Next page</button>-->
            </td>
        </tr>
    </table>
    <script src="js/products.js"></script>
</section>