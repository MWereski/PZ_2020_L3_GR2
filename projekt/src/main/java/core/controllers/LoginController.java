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
import user.models.Permission;
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
        User user = new User();
        user.setUsername("test1");
        user.setPassword("test");
        Permission userPermission = new Permission();
        userPermission.setContent("User");
        user.setPermissions(userPermission);

        User leader = new User();
        leader.setUsername("test2");
        leader.setPassword("test");
        Permission leaderPermission = new Permission();
        leaderPermission.setContent("Leader");
        leader.setPermissions(leaderPermission);

        User admin = new User();
        admin.setUsername("test3");
        admin.setPassword("test");
        Permission adminPermission = new Permission();
        adminPermission.setContent("Admin");
        admin.setPermissions(adminPermission);

        User[] users = {user, leader, admin};
        loginButton.setOnAction((event) -> {
            for (User u : users) {
                if (mockValidate(u)) {
                    try {
                        switch (u.getPermissions().getContent()) {
                            case "User": {
                                goNext(event, "userPanel.fxml");
                                break;
                            }
                            case "Leader": {
                                goNext(event, "leaderPanel.fxml");
                                break;
                            }
                            case "Admin": {
                                goNext(event, "adminPanel.fxml");
                                break;
                            }
                            default: {}
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private boolean mockValidate(User user) {
        return passwordField.getText().equals(user.getPassword()) && usernameField.getText().equals(user.getUsername());
    }

    public void goNext(ActionEvent e, String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass()).getResource("/user/layouts/" + filename));
        Parent application = loader.load();
        Scene applicationScene = new Scene(application);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(applicationScene);
        window.show();

    }
}