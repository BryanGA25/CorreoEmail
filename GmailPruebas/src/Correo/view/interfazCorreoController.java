package Correo.view;

import Correo.logica.Logica;
import Correo.model.EmailsMensage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class interfazCorreoController extends BaseController implements Initializable {

        ObservableList<EmailsMensage> listaCorreos;
        WebEngine webengine;

        @FXML
        private TableView<EmailsMensage> tablaCorreos;

        @FXML
        private WebView vistaEmail;

        public void logearse(){

                cargarDialogo("login.fxml",450,450).abrirDialogo(true);
                cargarTabla();


        }

        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
                webengine = vistaEmail.getEngine();
                webengine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                        @Override
                        public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State state, Worker.State t1) {
                                if (t1 == Worker.State.SUCCEEDED) {
                                        Document doc = webengine.getDocument();
                                        try {
                                                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                                                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                                                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                                                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                                                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                                                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                                                transformer.transform(new DOMSource(doc),
                                                        new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
                                        } catch (Exception ex) {
                                                ex.printStackTrace();
                                        }
                                }
                        }

                });



        }

        private void cargarTabla () {
                listaCorreos = new Logica().getInstance().getListaCorreos();
                tablaCorreos.setItems(listaCorreos);

        }

        public void mostrarCorreo() {

                webengine.load( String.valueOf(listaCorreos.get(1).getMensaje()));


        }
}
