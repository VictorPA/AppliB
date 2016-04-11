package sqlPlz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Victor on 11/04/2016.
 */
public class MyModel {

    private Connection connection;
    private int lastIdCompte;
    private String lastOperation;

    public MyModel(String url,String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public void ajoutOperation(int idCompte, int somme,String signe){

        try {
            Statement statement = connection.createStatement();
            boolean resultSet = statement.execute("BEGIN P_AJOUTOPERATION(" + idCompte + "," + signe+somme + "); END;");
            this.lastIdCompte = idCompte;
            this.lastOperation = signe+somme;
        } catch (SQLException e) {

            this.lastOperation = "Erreur : " + e.getMessage();

        }


    }


    public int getLastIdCompte() {
        return this.lastIdCompte;
    }

    public String getLastOperation() {
        return this.lastOperation;
    }
}
