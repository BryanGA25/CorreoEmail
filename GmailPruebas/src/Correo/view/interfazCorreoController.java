package Correo.view;

import Correo.logica.Logica;
import Correo.model.EmailTreeItem;
import Correo.model.Cuenta;

import Correo.model.EmailsMensage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.commons.mail.util.MimeMessageParser;
import org.w3c.dom.Document;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class interfazCorreoController extends BaseController implements Initializable {

        ObservableList<EmailsMensage> listaCorreos;
        WebEngine webengine;
        ArrayList<Cuenta> cuentas=new ArrayList<>();
        TreeItem raiz;
        int contador;
        private MimeMessageParser mime;

    @FXML
        private TreeView<String> TreeView;

        @FXML
        private TableView<EmailsMensage> tablaCorreos;

        @FXML
        private WebView vistaEmail;


        public void logearse() {

                cargarDialogo("login.fxml", 450, 450).abrirDialogo(true);
                cargarTabla();
                cuentas=Logica.getInstance().getCuentas();
                raiz=Logica.getInstance().crearTreeView(contador);
                TreeView.setRoot(raiz);

                


        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            tablaCorreos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmailsMensage>() {
                @Override
                public void changed(ObservableValue<? extends EmailsMensage> observableValue, EmailsMensage emailsMensage, EmailsMensage t1) {

                    mime=new MimeMessageParser((MimeMessage) t1.getMensaje());
                    vistaEmail.getEngine().loadContent(String.valueOf(mime));
                }
            });
            TreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> t1) {

                    String carpeta = "";
                    while(t1.getParent()!=null){
                        carpeta =  t1.toString() + "/" + carpeta;
                        t1 = t1.getParent();
                    }
                    StringBuilder str = new StringBuilder(carpeta);
                    str.delete(carpeta.length()-1, carpeta.length());
                    carpeta = str.toString();
                    System.out.println(carpeta);
                    tablaCorreos.setItems(Logica.getInstance().getListaCorreos(carpeta));


                }
            });
        }



                private void cargarTabla () {
                        listaCorreos = new Logica().getInstance().getListaCorreos("INBOX");
                        tablaCorreos.setItems(listaCorreos);

                }


                public void mostrarCorreo () {

                        webengine.load(String.valueOf(listaCorreos.get(1).getMensaje()));


                }
        }
