package Correo.view;

import Correo.logica.Logica;
import Correo.model.Cuenta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class loginController extends BaseController implements Initializable{

    private String usuario;
    private String contra;
    private Cuenta cuenta;
    @FXML
    private TextField correo;

    @FXML
    private TextField contraseña;

    @FXML
    void logearse(ActionEvent event) {
        usuario=correo.getText();
        contra=contraseña.getText();
        cuenta=new Cuenta(usuario,contra);
        Logica.getInstance().setCuenta(cuenta);
        Logica.getInstance().rootcreate(cuenta);
        getStage().close();
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        correo.setText("bryangallegoclases@gmail.com");
        contraseña.setText("250698tineo");

    }
}
