package Correo.view;

import Correo.logica.Logica;
import Correo.model.Cuenta;

import Correo.model.EmailsMensage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class interfazCorreoController extends BaseController implements Initializable {

        ObservableList<EmailsMensage> listaCorreos;

        ArrayList<Cuenta> cuentas=new ArrayList<>();
        TreeItem raiz;
        int contador=0;
        private MimeMessage mime;

    @FXML
        private TreeView<String> TreeView;

        @FXML
        private TableView<EmailsMensage> tablaCorreos;

        @FXML
        private WebView vistaEmail;


          public void logearse() {

                cargarDialogo("login.fxml", 600, 450).abrirDialogo(true);

                cuentas=Logica.getInstance().getCuentas();
                raiz=Logica.getInstance().getTree();
                TreeView.setRoot(raiz);
                TreeView.setShowRoot(false);
                cargarTabla();
                contador++;
                


        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                if(tablaCorreos.getSelectionModel()!=null) {
            tablaCorreos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmailsMensage>() {
                @Override
                public void changed(ObservableValue<? extends EmailsMensage> observableValue, EmailsMensage emailsMensage, EmailsMensage t1) {

                                MimeMessageParser mine = Logica.getInstance().getMimeMessageParser(t1.getMensaje());
                                vistaEmail.getEngine().loadContent(mine.getHtmlContent());
                        }


            });
        }
                if (tablaCorreos.getSelectionModel()!=null) {
                        TreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                                @Override
                                public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> t1) {


                                        if (t1.getValue().equalsIgnoreCase("INBOX")) {
                                                Logica.getInstance().getListaCorreos(t1.getValue());
                                        } else {
                                                String carpeta = "[Gmail]/" + t1.getValue();
                                                Logica.getInstance().getListaCorreos(carpeta);
                                        }


                                }

                        });
                }
        }



                private void cargarTabla () {
                        listaCorreos = new Logica().getInstance().getListaCorreos("INBOX");
                        tablaCorreos.setItems(listaCorreos);

                }


        }
