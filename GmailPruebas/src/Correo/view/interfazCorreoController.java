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
import org.w3c.dom.Document;

import javax.mail.Folder;
import javax.mail.MessagingException;
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
        int cont;
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
                crearTreeView();
                TreeView.setRoot(raiz);

                


        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

               TreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                       @Override
                       public void changed(ObservableValue<? extends TreeItem<String>> observableValue,
                                           TreeItem<String> stringTreeItem, TreeItem<String> t1) {
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
