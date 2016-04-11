package sqlPlz;

import guiSQL.MyView;

import java.sql.*;

/**
 * Created by Victor on 10/04/2016.
 */
public class AppliAjoutOperation {



    public static void main(String[] args) throws SQLException {

        MyModel model = new MyModel("jdbc:oracle:thin:@//localhost:1521/XE","SYSTEM", "868a73be");
        MyController controller = new MyController(model);
        MyView view = new MyView(controller);
        view.setVisible(true);
        controller.setView(view);

    }
}
