package programacion.avanzada.programacion_avanzada_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML
    private TableView<UserModel> tblUsuarios;

    @FXML
    private TableColumn<UserModel, String> colEmailUsuario;

    @FXML
    private TableColumn<UserModel, String> colNombreUsuario;

    @FXML
    private TableColumn<UserModel, String> colRolUsuario;

    @FXML
    private TableColumn<UserModel, String> colTelefonoUsuario;

    @FXML
    private TableColumn<UserModel, String> colUsuario;

    @FXML
    private ComboBox<String> cbbRol;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtUsuario;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnNuevo;

    public void logout() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deseas cerrar sesion?");
        alert.setHeaderText("Cerrar sesion");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get().equals(ButtonType.OK)){
            this.redirectToLogin();
        }
    }

    public void redirectToLogin() throws IOException {
        btnLogout.getScene().getWindow().hide();
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader);

        stage.setScene(scene);
        stage.show();
    }

    private void cargarUsuarios() {
        List<UserModel> userList = UserRepository.readUsers(); // Obtener datos desde el JSON
        ObservableList<UserModel> observableList = FXCollections.observableArrayList(userList);
        tblUsuarios.setItems(observableList);
    }

    public void editarUsuario() {
        UserModel user = tblUsuarios.getSelectionModel().getSelectedItem();
        int index = tblUsuarios.getSelectionModel().getSelectedIndex();

        if((index - 1) < -1){return;}

        txtUsuario.setDisable(true);

        txtUsuario.setText(user.getUsuario());
        txtNombre.setText(user.getNombre());
        cbbRol.setValue(user.getRol());
        txtEmail.setText(user.getEmail());
        txtTelefono.setText(user.getTelefono());
        txtPassword.setText(user.getPassword());
    }

    public void guardarUsuario() {
        UserModel user = tblUsuarios.getSelectionModel().getSelectedItem();
        int index = tblUsuarios.getSelectionModel().getSelectedIndex();
        Alert alert;

        if(txtUsuario.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campo requerido!");
            alert.setHeaderText(null);
            alert.setContentText("El campo [Usuario] es requerido!");
            alert.showAndWait();
            return;
        }

        if(txtNombre.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campo requerido!");
            alert.setHeaderText(null);
            alert.setContentText("El campo [Nombre] es requerido!");
            alert.showAndWait();
            return;
        }

        if(cbbRol.getValue() == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campo requerido!");
            alert.setHeaderText(null);
            alert.setContentText("El campo [Rol] es requerido!");
            alert.showAndWait();
            return;
        }

        if(txtPassword.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campo requerido!");
            alert.setHeaderText(null);
            alert.setContentText("El campo [Contraseña] es requerido!");
            alert.showAndWait();
            return;
        }

        if((index - 1) < -1){
            this.crearUsuario();
        } else {
            this.actualizarUsuario();
        }
    }

    public void crearUsuario() {
        UserModel user = new UserModel(
            txtUsuario.getText(),
            txtNombre.getText(),
            (String)cbbRol.getSelectionModel().getSelectedItem(),
            txtEmail.getText(),
            txtTelefono.getText(),
            txtPassword.getText()
        );

        Alert alert;
        boolean status = UserRepository.createUser(user);
        if(!status){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("El usuario ["+user.getUsuario()+"] ya existe");
            alert.showAndWait();
            return;
        }

        this.cargarUsuarios();
        this.limpiarFormulario();
    }

    public void actualizarUsuario(){
        UserModel user = new UserModel(
            txtUsuario.getText(),
            txtNombre.getText(),
            (String)cbbRol.getSelectionModel().getSelectedItem(),
            txtEmail.getText(),
            txtTelefono.getText(),
            txtPassword.getText()
        );

        Alert alert;
        boolean status = UserRepository.updateUser(user.getUsuario(), user);
        if(!status){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrio un error al actualizar el usuario!");
            alert.showAndWait();
            return;
        }
        this.cargarUsuarios();
        this.limpiarFormulario();
    }

    public void limpiarFormulario(){
        tblUsuarios.getSelectionModel().clearSelection();
        txtUsuario.setDisable(false);

        txtUsuario.clear();
        txtNombre.clear();
        txtPassword.clear();
        cbbRol.getSelectionModel().clearSelection();
        txtEmail.clear();
        txtTelefono.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRolUsuario.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colEmailUsuario.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefonoUsuario.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        cbbRol.setItems(FXCollections.observableArrayList(
            "ADMINISTRADOR","PROFESOR","ESTUDIANTE"
        ));

        this.cargarUsuarios();
    }
}
