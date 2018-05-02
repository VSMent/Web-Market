<section id="block_user"> 

    <%
        //Utilizamos una variable en la sesión para informar de los mensajes de Error
        String message = (String) session.getAttribute("message");
        if (message != null) //Eliminamos el mensaje consumido
        {
            session.removeAttribute("message");
        }
        //Se obtiene el usuario actual registrado en el servicio web, del entorno de sesión
        String user = (String) session.getAttribute("user");
        String password = (String) session.getAttribute("password");
        Boolean isUserComplete = (Boolean) session.getAttribute("isUserComplete");

        String uName = "";
        String uSurname = "";
        String uPhone = "";
        String uCountry = "";
        String uCity = "";
        String uAddress = "";
        String uPassport = "";

        if (isUserComplete == null) {
            isUserComplete = false;
        }

        if (isUserComplete) {
            uName = (String) session.getAttribute("name");
            uSurname = (String) session.getAttribute("surname");
            uPhone = (String) session.getAttribute("phone");
            uCountry = (String) session.getAttribute("country");
            uCity = (String) session.getAttribute("city");
            uAddress = (String) session.getAttribute("address");
            uPassport = (String) session.getAttribute("passport");
        }

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
        <input class="loginInput" name="logout" value="Send" type="submit">
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
        <input class="loginInput" id="userMail" name="email" type="email" value="<%=user%>" readonly required><br>
        <label for="userPass">Password</label>
        <input class="loginInput" id="userPass" name="password" type="password" value="<%=password%>" readonly required><br>
        <label for="userName">Name</label>
        <input class="loginInput" id="userName" name="name" type="text" maxlength="100" value="<%=uName%>" required><br>
        <label for="userSurname">Surname</label>
        <input class="loginInput" id="userSurname" name="surname" type="text" maxlength="100" value="<%=uSurname%>" required><br>
        <label for="userPhone">Phone number</label>
        <input class="loginInput" id="userPhone" name="phone" type="tel" maxlength="15" pattern="[0-9]{3,10}" title="Only (3-10) numbers" value="<%=uPhone%>" required><br>
        <label for="userCountry">Country</label>
        <input class="loginInput" id="userCountry" name="country" type="text" maxlength="100" value="<%=uCountry%>" required><br>
        <label for="userCity">City</label>
        <input class="loginInput" id="userCity" name="city" type="text" maxlength="100" value="<%=uCity%>" required><br>
        <label for="userAddress">Address</label>
        <input class="loginInput" id="userAddress" name="address" maxlength="100" type="text" value="<%=uAddress%>" required><br>
        <label for="userPassport">Passport</label>
        <input class="loginInput" id="userPassport" name="passport" maxlength="10" type="text" value="<%=uPassport%>" required><br>
        <label for="fullChange">Edit Email and Password</label>
        <input class="loginInput" id="fullChange" name="fullChange" type="checkbox">
        <input name="actionType" value="fullInfo" type="hidden" checked required>
        <%
            if (isUserComplete) {
        %>    
        <input class="loginInput" name="logout" value="Change" type="submit"><br>
        <input id="logOut" class="loginInput" name="logout" value="Log Out" type="submit"><br>
        <%
        } else {
        %>
        <input class="loginInput" name="logout" value="Send" type="submit"><br>
        <%
            }
        %>
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