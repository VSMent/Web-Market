package p2;

import java.sql.*;

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

}
