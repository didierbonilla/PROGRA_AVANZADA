package programacion.avanzada.programacion_avanzada_project;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    public void login(){

        Alert alert;

        try {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            UserModel user = UserRepository.findUserByUsername(username);
            if(user == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inicio de sesion fallido...");
                alert.setHeaderText(null);
                alert.setContentText("Usuario no existe");
                alert.showAndWait();
                return;
            }

            if(!user.getPassword().equals(password)){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inicio de sesion fallido...");
                alert.setHeaderText(null);
                alert.setContentText("Usuario o Contraseña incorrecta");
                alert.showAndWait();
                return;
            }

            this.redirectToMain();
        }
        catch (Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Error!");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void redirectToMain() throws IOException {
        btnLogin.getScene().getWindow().hide();
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader);

        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
