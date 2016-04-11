package sqlPlz;


import guiSQL.MyView;

import java.sql.SQLException;

/**
 * Created by Victor on 11/04/2016.
 */
public class MyController {
    private MyModel model;
    private MyView view;
    private int result;

    public MyController(MyModel myModel) {
        this.model = myModel;
    }

    public void send(int idCompteInput, int montantOperationInput, int playerInput) throws SQLException {
        String signe;
        if(playerInput==0)
            signe = "+";
        else
            signe = "-";


        model.ajoutOperation(idCompteInput,montantOperationInput,signe);
        this.view.receive(model.getLastIdCompte(),model.getLastOperation());

    }

    public void setView(MyView myView){
        this.view = myView;
    }
}
