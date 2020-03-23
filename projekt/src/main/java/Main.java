import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import task.models.Task;
import task.models.TaskDAO;
import user.models.Permission;
import user.models.PermissionDAO;
import user.models.User;
import user.models.UserDAO;


public class Main extends Application {
    public void start(Stage stage) throws Exception {
        stage.setTitle("Schronisko");
        stage.setResizable(false);
        stage.setMaxHeight(454.0);
        stage.setMaxWidth(762.0);
        stage.setX(650.0);
        stage.setY(225.0);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/core/layouts/login.fxml"));
        AnchorPane layout = loader.load();
        Scene scene = new Scene(layout);
        // scene.getStylesheets().add("com/company/application.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // setMockData();
        launch(args);
    }

    /**
     * Method that mocks testing data
     * IMPORTANT! Can be runned only once
     */
    static void setMockData() {
//        // Mocking User
//        User u = new User();
//        u.setFirstName("Adam");
//        u.setLastName("Testowy");
//        u.setUsername("adamjestem");
//        u.setPassword("testowe");
//        u.setPermissions(new HashSet<>());
//        UserDAO uDAO = new UserDAO();
//
//        // Mocking Permission
//        Permission p = new Permission();
//        p.setContent("elemele");
//        p.setUsers(new HashSet<>());
//        PermissionDAO pDAO = new PermissionDAO();
//
//
//        // Mocking Task
//        Task task = new Task();
//        task.setContent("Do something");
//
//        //Saving it
//        try {
//            uDAO.save(u);
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            pDAO.save(p);
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//            System.out.println("Database error");
//        }
//
//        TaskDAO tDAO = new TaskDAO();
//        tDAO.save(task);

        UserDAO uDao = new UserDAO();
        User u2 = uDao.get(1);
        PermissionDAO pDao = new PermissionDAO();
        Permission p2 = pDao.get(1);
        uDao.addPermission(u2, p2);
        TaskDAO taskDAO = new TaskDAO();
        Task task = taskDAO.get(1);
        uDao.addTask(u2, task);
    }
}
