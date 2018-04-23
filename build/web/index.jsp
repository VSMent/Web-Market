<!DOCTYPE html>
<html>
    <head>
        <title>Company main page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="style/style.css">
        <link rel="shortcut icon" href="images/favicon2.ico">
    </head>
    <body>
        <%--<jsp:include page="<%="includes/"+p+".jsp"%>"/>--%>
        <jsp:include page="<%="pages/header.jsp"%>"/>

        <jsp:include page="<%="pages/main.jsp"%>"/>
        <jsp:include page="<%="pages/about.jsp"%>"/>
        <jsp:include page="<%="pages/contacts.jsp"%>"/>
        <jsp:include page="<%="pages/products.jsp"%>"/>
        <jsp:include page="<%="pages/cart.jsp"%>"/>
        <jsp:include page="<%="pages/user.jsp"%>"/>
        <jsp:include page="<%="pages/admin.jsp"%>"/>
        <script
            src="http://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"></script>
        <script src="js/libCapas.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>