<section id="block_user"> 

    <%
        //Utilizamos una variable en la sesión para informar de los mensajes de Error
        String message = (String) session.getAttribute("message");
        if (message != null) //Eliminamos el mensaje consumido
        {
            session.removeAttribute("message");
        }
        else message = "Test error";
        //Se obtiene el usuario actual registrado en el servicio web, del entorno de sesión
//        String user = "asd.j@f";
        String user = (String) session.getAttribute("user");
//        String password = "12345";
        String password = (String) session.getAttribute("password");
//        Boolean isUserComplete = false;
        Boolean isUserComplete = (Boolean) session.getAttribute("isUserComplete");

        if (user == null) //No hay usuario registrado
        {
            //Mostramos el formulario para la introducción del usuario y la clave
    %>
    <h1>Login here:</h1>
    <form class="siteF" method="POST" action="LoginUser">
        <label for="userLoginMail">Email</label>
        <input class="loginInput" id="userLoginMail" name="email" type="email" required><br>
        <label for="userLoginPass">Password</label>
        <input class="loginInput" id="userLoginPass" name="password" type="password"  required><br>
        <label for="register">Register</label>
        <input class="loginInput" id="register" name="actionType" value="register" type="radio" required>
        <label for="login">Log in</label>
        <input class="loginInput" id="login" name="actionType" value="login" type="radio" checked>
        <input class="loginInput" name="submit" value="Send" type="submit">
        <%
            if (message != null) {
        %>
        <p class="error"><%=message%></p>
        <%
            }
        %>
    </form>
    <%
    } else {
        if (isUserComplete) {
    %>    
    <h1>Your info</h1>
    <%
    } else {
    %>
    <h1>We need some more data to proceed</h1>
    <%
        }
    %>
    <form class="siteF userData" method="POST" action="LoginUser">
        <label for="userMail">Email</label>
        <input class="loginInput" id="userMail" name="mail" type="email" value="<%=user%>" readonly><br>
        <label for="userPass">Password</label>
        <input class="loginInput" id="userPass" name="pass" type="password" value="<%=password%>" readonly><br>
        <label for="userName">Name</label>
        <input class="loginInput" id="userName" name="name" type="text" maxlength="100" required><br>
        <label for="userSurname">Surname</label>
        <input class="loginInput" id="userSurname" name="surname" type="text" maxlength="100"><br>
        <label for="userPhone">Phone number</label>
        <input class="loginInput" id="userPhone" name="phone" type="tel" maxlength="15" pattern="[0-9]" title="Only numbers" required><br>
        <label for="userCountry">Country</label>
        <input class="loginInput" id="userCountry" name="country" type="text" maxlength="100" required><br>
        <label for="userCity">City</label>
        <input class="loginInput" id="userCity" name="city" type="text" maxlength="100" required><br>
        <label for="userAddress">Address</label>
        <input class="loginInput" id="userAddress" name="address" maxlength="100" type="text" required><br>
        <label for="userPassport">Passport</label>
        <input class="loginInput" id="userPassport" name="passport" maxlength="10" type="text" required><br>
        <label for="fullChange">Edit Email and Password</label>
        <input class="loginInput" id="fullChange" name="fullChange" type="checkbox">
        <input class="loginInput" name="submit" value="Send" type="submit"><br>
        <%
            if (message != null) {
        %>
        <p class="error"><%=message%></p>
        <%
            }
        %>
    </form>    
    <%
        }
    %> 
    <script src="js/user.js"></script>
</section>