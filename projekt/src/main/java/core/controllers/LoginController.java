package core.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import user.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label alertLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User x = new User();
        x.setUsername("test");
        x.setPassword("test");
        loginButton.setOnAction((event) -> {
            boolean result = mockValidate(usernameField.getText(), passwordField.getText());
            if(result) {
                try{
                    goNext(event);
                }
              catch (IOException exception){
                    exception.printStackTrace();
              }
            } else {
                alertLabel.setVisible(true);
            }
        });

    }

    private boolean mockValidate(String username, String password){
        return password.equals("test") && username.equals("test");
    }

    public void goNext(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass()).getResource("/user/layouts/Home.fxml"));
        Parent application = loader.load();
        Scene applicationScene = new Scene(application);
        Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
        window.setScene(applicationScene);
        window.show();

    }
}