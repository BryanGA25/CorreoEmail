package Correo.view;

import Correo.logica.Logica;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.ResourceBundle;

public class enviarController extends BaseController implements Initializable {

    @FXML
    private HTMLEditor htmleditor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void enviarCorreo(){
        Logica.getInstance().enviarCorreo();
    }
}
