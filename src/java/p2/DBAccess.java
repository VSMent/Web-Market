package p2;

import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBAccess {

    private static DBAccess uniqueDB = null;
    private Connection conectionDB = null;

    public DBAccess() {
        openConnectionDB();
    }

    public static DBAccess getInstance() {
        if (uniqueDB == null) {
            uniqueDB = new DBAccess();
        }
        return uniqueDB;
    }

    public void openConnectionDB() {
        if (conectionDB == null) { // company es el nombre de la base de datos que hemos creado con anterioridad.
            String nombreConexionBD = "jdbc:mysql://localhost/company";
            try { // root y sin clave es el usuario por defecto que crea XAMPP.
                Class.forName("com.mysql.jdbc.Driver");
                conectionDB = DriverManager.getConnection(nombreConexionBD, "root", "root");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Can not access DB");
            }
        }
    }

    public void closeConnectionDB() {
        if (conectionDB != null) {
            try {
                conectionDB.close();
                conectionDB = null;
            } catch (SQLException e) {
                //Error en la conexión con la BD
                System.out.println("Disconnection was not complete");
            }
        }
    }

    public ResultSet getProductsSet() {
        openConnectionDB();

        ResultSet result = null;
        try {
            String con;
            Statement s = conectionDB.createStatement();
            con = "SELECT id,image,name,description,price,in_pack,stock FROM products";
            // hay que tener en cuenta las columnas de vuestra tabla de productos
            // también se puede utilizar una consulta del tipo:
            // con = "SELECT * FROM productos";
            result = s.executeQuery(con);
        } catch (SQLException e) {
            System.out.println("Error ejecutando la consulta a la BB.DD....");
        }
        return result;
    }

    public boolean accessTest() {
        openConnectionDB();
        return conectionDB != null;
    }

    public boolean checkUserDB(String email, String password, int mode) {
        openConnectionDB();

        ResultSet results = null;
        try {
            String con;
            Statement s = conectionDB.createStatement();
            //Consulta, buscamos una correspondencia usuario/clave
            if (mode == 0) {
                con = "SELECT * FROM users WHERE email='" + email + "' and password='" + password + "'";
            } else {
                con = "SELECT * FROM users WHERE email='" + email + "'";
            }
            results = s.executeQuery(con);

            if (results.next()) //El usuario/clave se encuentra en la BD
            {
                return true;
            } else //El usuario/clave no se encuentra en la BD
            {
                return false;
            }
        } catch (Exception e) {
            //Error en la conexión con la BD
            System.out.println("No se ha completado la peticion...");
            return false;
        }
    }

    public boolean registerUser(String email, String password, String name, String surname, String phoneNumber, String country, String city, String address, String passport) {
        openConnectionDB();

        try {
            String insertString;
            Statement s = conectionDB.createStatement();
            //Consulta, buscamos una correspondencia usuario/clave
            insertString = "INSERT INTO users ("
                    + "email, "
                    + "password, "
                    + "name, "
                    + "surname, "
                    + "phone, "
                    + "country, "
                    + "city, "
                    + "address, "
                    + "passport)"
                    + " VALUES ('"
                    + email + "', '"
                    + password + "', '"
                    + name + "', '"
                    + surname + "', '"
                    + phoneNumber + "', '"
                    + country + "', '"
                    + city + "', '"
                    + address + "', '"
                    + passport + "')";
            s.executeUpdate(insertString);
            return true;

        } catch (Exception e) {
            //Error en la conexión con la BD
            System.out.println("No se ha completado la peticion...");
            return false;
        }
    }

    public ResultSet loginUser(String email, String password) {
        openConnectionDB();

        ResultSet user = null;
        try {
            String con;
            Statement s = conectionDB.createStatement();

            con = "SELECT * FROM users WHERE email='" + email + "' and password='" + password + "'";

            user = s.executeQuery(con);
        } catch (Exception e) {
            //Error en la conexión con la BD
            System.out.println("No se ha completado la peticion...");
        }
        return user;
    }

    public boolean updateUser(String email, String password, String name, String surname, String phoneNumber, String country, String city, String address, String passport, int id) {
        openConnectionDB();

        try {
            String updateString;
            Statement s = conectionDB.createStatement();
            //Consulta, buscamos una correspondencia usuario/clave
            updateString = "UPDATE users SET "
                    + "email = '"
                    + email + "', "
                    + "password = '"
                    + password + "', "
                    + "name = '"
                    + name + "', "
                    + "surname = '"
                    + surname + "', "
                    + "phone = '"
                    + phoneNumber + "', "
                    + "country = '"
                    + country + "', "
                    + "city = '"
                    + city + "', "
                    + "address = '"
                    + address + "', "
                    + "passport = '"
                    + passport + "' "
                    + "WHERE id = "
                    + id;
            s.executeUpdate(updateString);
            return true;

        } catch (Exception e) {
            //Error en la conexión con la BD
            System.out.println("No se ha completado la peticion...");
            return false;
        }
    }

    public Integer getUserId(String email, String password) {
        openConnectionDB();

        ResultSet user;
        Integer userId = null;
        try {
            String con;
            Statement s = conectionDB.createStatement();
            con = "SELECT id FROM users WHERE email='" + email + "' and password='" + password + "'";

            user = s.executeQuery(con);

            user.next();
            userId = Integer.parseInt(user.getString("id"));
        } catch (Exception e) {
            //Error en la conexión con la BD
            System.out.println("No se ha completado la peticion...");
        }

        return userId;
    }

    public String buyProducts(int userId, String cart) {
        String message = "";

        JSONArray products = new JSONArray(cart);

        openConnectionDB();

        ResultSet DBProducts = null;
        String con;

        int productsLength = products.length();
        boolean notEnough = false;

        String productIds = "";
        for (int i = 0, l = productsLength; i < l; i++) {
            JSONObject obj = products.getJSONObject(i);
            if (i == l - 1) {
                productIds += obj.getInt("id");
            } else {
                productIds += (obj.getInt("id") + ", ");
            }
        }

        con = "SELECT id,stock FROM products WHERE id IN (" + productIds + ")";

        try {
            Statement s = conectionDB.createStatement();

            DBProducts = s.executeQuery(con);

            while (DBProducts.next()) { // check all products
                productsLength--;
            }

            if (productsLength == 0) {    // request returned all products
                DBProducts.beforeFirst();
                productsLength = products.length();

                // check stock
                while (DBProducts.next()) {
                    for (int i = 0, l = productsLength; i < l; i++) {
                        JSONObject obj = products.getJSONObject(i);
                        // check if product id == DBProduct id
                        if (obj.getInt("id") == DBProducts.getInt("id")) {
                            // check if user amount less than stock amount
                            if (DBProducts.getInt("stock") - obj.getInt("amount") < 0) {
                                // not enough in stock
                                message += obj.getString("name") + " amount is " + DBProducts.getInt("stock") + " you want " + obj.getInt("amount") + " which is not enough<br>";
                                notEnough = true;
                            }
                            break;
                        }
                    }
                }
                if (!notEnough) {
                    DBProducts.beforeFirst();

                    // create purch
                    Date date = new Date();
                    java.sql.Timestamp dateTime = new java.sql.Timestamp(date.getTime());

                    Statement s2 = conectionDB.createStatement();
                    String purchaseString;
                    int purchaseId = -1;
                    float totalPrice = 0;
                    purchaseString = "INSERT INTO purchases ("
                            + "date, "
                            + "status, "
                            + "user_id, "
                            + "total_price) "
                            + "VALUES ('"
                            + dateTime + "', '"
                            + "Opened', "
                            + userId + ", "
                            + totalPrice + ")";
                    s2.executeUpdate(purchaseString, Statement.RETURN_GENERATED_KEYS);
//
                    ResultSet generatedKeys = s2.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        purchaseId = generatedKeys.getInt(1);
                    }

                    // create details
                    String detailString = "";
                    String updateProduct = "";
                    while (DBProducts.next()) {
                        for (int i = 0, l = productsLength; i < l; i++) {
                            JSONObject obj = products.getJSONObject(i);
                            // check if product id == DBProduct id
                            if (obj.getInt("id") == DBProducts.getInt("id")) {
                                // check if user amount less than stock amount
                                int newStock = DBProducts.getInt("stock") - obj.getInt("amount");
                                if (newStock >= 0) {
                                    // create detail
                                    totalPrice += obj.getInt("amount") * obj.getInt("price");
                                    detailString = "INSERT INTO details ("
                                            + "amount, "
                                            + "price, "
                                            + "product_id, "
                                            + "purchase_id) "
                                            + " VALUES ("
                                            + obj.getInt("amount") + ", "
                                            + obj.getInt("amount") * obj.getInt("price") + ", "
                                            + obj.getInt("id") + ", "
                                            + purchaseId + ")";
                                    s2.executeUpdate(detailString);

                                    // decrease stock                                    
                                    updateProduct = "UPDATE products SET "
                                            + "stock = "
                                            + newStock + " "
                                            + "WHERE id = "
                                            + obj.getInt("id");
                                    s2.executeUpdate(updateProduct);
                                } else {
                                    // not enough product
                                    message += obj.getString("name") + " amount is not enough\n";
                                }
                                break;
                            }
                        }
                    }

                    // update purch with total price
                    String updatePurchase;
                    updatePurchase = "UPDATE purchases SET "
                            + "total_price = '"
                            + totalPrice + "' "
                            + "WHERE id = "
                            + purchaseId;
                    s2.executeUpdate(updatePurchase);

                    // success if message was not changed
                    if (message == "") {
                        message = "Success";
                    }
                }
                return message;
            } else {  // some products does not exist
                message = "client error";
                return message;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            message = "db error";
            return message;
        }

    }
}
