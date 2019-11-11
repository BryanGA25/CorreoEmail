package Correo.view;

import Correo.logica.Logica;
import Correo.model.EmailsMensage;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class interfazCorreoController extends BaseController implements Initializable {

        ObservableList<EmailsMensage> listaCorreos;

        @FXML
        private TableView<EmailsMensage> tablaCorreos;

        @FXML
        private WebView vistaEmail;

        public void logearse(){

                cargarDialogo("login.fxml",450,450).abrirDialogo(true);
                cargarTabla();

        }





        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {




        }

        private void cargarTabla() {
                listaCorreos= new Logica().getInstance().getListaCorreos();
                tablaCorreos.setItems(listaCorreos);

        }
}
