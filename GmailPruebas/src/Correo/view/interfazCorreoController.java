package Correo.view;

import Correo.logica.Logica;
import Correo.model.Cuenta;

import Correo.model.EmailTreeItem;
import Correo.model.EmailsMensage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class interfazCorreoController extends BaseController implements Initializable {

    ObservableList<EmailsMensage> listaCorreos;

    ArrayList<Cuenta> cuentas = new ArrayList<>();
    TreeItem raiz;
    private MimeMessage mime;


    @FXML
    ComboBox<String> temas;
    @FXML
    private TreeView<String> TreeView;

    @FXML
    private TableView<EmailsMensage> tablaCorreos;

    @FXML
    private WebView vistaEmail;

    @FXML
    void borrarCorreo(ActionEvent event) {


            EmailTreeItem emailTreeItem = (EmailTreeItem)TreeView.getSelectionModel().getSelectedItem();
            EmailsMensage email = tablaCorreos.getSelectionModel().getSelectedItem();
            Folder folder = emailTreeItem.getFolder();
            Cuenta mailAccount =emailTreeItem.getEmailAccount();

            Logica.getInstance().deleteMail(email, folder, mailAccount);




    }

    public void logearse() {

        cargarDialogo("login.fxml", 600, 450).abrirDialogo(true);

        cuentas = Logica.getInstance().getCuentas();
        raiz = Logica.getInstance().getTree();
        TreeView.setRoot(raiz);

        TreeView.setShowRoot(false);
        cargarTabla();

    }

    public void enviar() {

        BaseController controller = cargarDialogo("correo.fxml", 800, 600);
        ((enviarController) controller).sender((EmailTreeItem)TreeView.getSelectionModel().getSelectedItem());
        controller.abrirDialogo(true);
        cargarDialogo("correo.fxml", 800, 800).abrirDialogo(true);
    }


    public void reenviar(ActionEvent event) {


        EmailsMensage mensaje = tablaCorreos.getSelectionModel().getSelectedItem();
        BaseController controller = cargarDialogo("correo.fxml", 800, 600);
        ((enviarController) controller).sender((EmailTreeItem)TreeView.getSelectionModel().getSelectedItem());
        ((enviarController) controller).reenviar(mensaje);
        controller.abrirDialogo(true);


    }

    public void responder(ActionEvent event) {

        EmailsMensage mensaje = tablaCorreos.getSelectionModel().getSelectedItem();
        BaseController controller = cargarDialogo("correo.fxml", 800, 600);
        ((enviarController) controller).sender((EmailTreeItem)TreeView.getSelectionModel().getSelectedItem());
        ((enviarController) controller).responder(mensaje);
        controller.abrirDialogo(true);
    }

    private void cargarTabla() {

        listaCorreos = new Logica().getInstance().getListaCorreos("INBOX");
        tablaCorreos.setItems(listaCorreos);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        temas.setPromptText("Estilos");
        temas.getItems().addAll(Application.STYLESHEET_MODENA, Application.STYLESHEET_CASPIAN);
        temas.getSelectionModel().select(Application.getUserAgentStylesheet());
        temas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                Application.setUserAgentStylesheet(newValue);
            }
        });

        tablaCorreos.setRowFactory(new Callback<TableView<EmailsMensage>, TableRow<EmailsMensage>>() {
            @Override
            public TableRow<EmailsMensage> call(TableView<EmailsMensage> emailsMensageTableView) {
                return new TableRow<EmailsMensage>() {
                    @Override
                    protected void updateItem(EmailsMensage emailsMensage, boolean b) {
                        super.updateItem(emailsMensage, b);
                        if (emailsMensage != null) {
                            if (!emailsMensage.leido()) {
                                setStyle("-fx-font-weight:bold");
                            } else {
                                setStyle("");
                            }
                        }
                    }


                };


            }
        });




            tablaCorreos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmailsMensage>() {
                @Override
                public void changed(ObservableValue<? extends EmailsMensage> observableValue, EmailsMensage emailsMensage, EmailsMensage t1) {

                    String mensaje = t1.getContenido();
                    WebEngine web= vistaEmail.getEngine();
                    web.loadContent(mensaje);
                }


            });


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









