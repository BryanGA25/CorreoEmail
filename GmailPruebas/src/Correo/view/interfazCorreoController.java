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
import org.apache.commons.mail.Email;
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

        private ObservableList<EmailsMensage> listaCorreos;
        private WebEngine webengine;
        private ArrayList<Cuenta> cuentas=new ArrayList<>();
        private TreeItem raiz;
        private MimeMessageParser mime;

        int cont;
        @FXML
        private TreeView<String> TreeView;

        @FXML
        private TableView<EmailsMensage> tablaCorreos;

        @FXML
        private WebView vistaEmail=new WebView();


        public void logearse() {

                cargarDialogo("login.fxml", 600, 450).abrirDialogo(true);
                cargarTabla();
                cuentas=Logica.getInstance().getCuentas();
                crearTreeView();
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

                        }
                });



        }




        public void crearTreeView() {
                for (cont = 0; cont < cuentas.size(); cont++) {
                        try {
                                raiz = new EmailTreeItem(cuentas.get(cont).getCuenta(), cuentas.get(cont), Logica.getInstance().getFolder());
                                getFolder(((EmailTreeItem)raiz).getFolder().list(), (EmailTreeItem)raiz);
                        } catch (MessagingException e) {
                                e.printStackTrace();
                        }
                }
        }
        private void getFolder(Folder[] folders,EmailTreeItem objeto) {
                for (Folder folder : folders) {
                        EmailTreeItem emailTreeItem = new EmailTreeItem(folder.getName(),cuentas.get(cont) , folder);
                        objeto.getChildren().add(emailTreeItem);
                        try {
                                if(folder.getType() == Folder.HOLDS_FOLDERS){
                                        getFolder(folder.list(), emailTreeItem  );
                                }
                        } catch (MessagingException e) {
                                e.printStackTrace();
                        }
                }
        }

                private void cargarTabla () {
                        listaCorreos = new Logica().getInstance().getListaCorreos("INBOX");
                        tablaCorreos.setItems(listaCorreos);

                }


                public void mostrarCorreo () {

                        webengine.load(String.valueOf(listaCorreos.get(1).getMensaje()));


                }
        }
