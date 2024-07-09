package register.fxml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeController implements Initializable {

    @FXML
    private TableView<User> userListTV;

    @FXML
    private TableColumn<User, String> usernameCol;

    @FXML
    private TableColumn<User, String> fullnameCol;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField fullnameTF;

    @FXML
    private Label welcomeMsg;

    @FXML
    private ImageView imgView;

    private User loginedUser;
    private ObservableList<User> obsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Nội dung khi mở màn hình Home
        Platform.runLater(() -> {
            welcomeMsg.setText("Xin chào tao là MD còn mày là " + loginedUser.getFullname());
        });

        // Bắt cặp
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        fullnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("fullname"));

        // Lấy danh sách User từ CSDL
        List<User> listUser = UserDAO.listAllUser();
        obsList = FXCollections.observableArrayList(listUser);
        // Đưa vào bảng TableView
        userListTV.setItems(obsList);
    }

    @FXML
    public void onClickRow() {
        User selectedUser = userListTV.getSelectionModel().getSelectedItem();
        emailTF.setText(selectedUser.getEmail());
        fullnameTF.setText(selectedUser.getFullname());
        // Đưa ảnh vào ImageView
        FileInputStream input;
        try {
            if (selectedUser.getImgPath() != null) {
                input = new FileInputStream(selectedUser.getImgPath());
                Image image = new Image(input);
                imgView.setImage(image);
            } else {
                imgView.setImage(null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Không lấy được ảnh");
        }
    }
    
 

    @FXML
    public void onClickAdd() {
        User user = new User(emailTF.getText(), fullnameTF.getText());
        boolean addResult = UserDAO.addUser(user);

        if (addResult) {
           
        	userListTV.getItems().add(user);
            System.out.println("Add successful!");
            welcomeMsg.setText("Add successful!");
        } else {
            System.out.println("Error adding to database!");
            welcomeMsg.setText("Error adding to database!");
        }
    }
//
//    @FXML
//    public void onClickAdjust() {
//        int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
//        if (selectedIndex >= 0) {
//            User selectedUser = userListTV.getItems().get(selectedIndex);
//            selectedUser.setEmail(emailTF.getText());
//            selectedUser.setFullname(fullnameTF.getText());
//
//            boolean updateResult = UserDAO.adjustUser(selectedUser);
//
//            if (updateResult) {
//                userListTV.getItems().set(selectedIndex, selectedUser);
//                System.out.println("Update successful!");
//                welcomeMsg.setText("Update successful!");
//            } else {
//                System.out.println("Error updating database!");
//                welcomeMsg.setText("Error updating database!");
//            }
//        } else {
//            showAlert("No selection", "No User Selected", "Please select a user in the table.");
//        }
//    }
    @FXML
    public void onClickAdjust() {
        int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User selectedUser = userListTV.getItems().get(selectedIndex);
            selectedUser.setEmail(emailTF.getText());
            selectedUser.setFullname(fullnameTF.getText());
            // Đảm bảo rằng ID được thiết lập đúng
            selectedUser.setId(selectedIndex); // Hoặc phương thức khác để lấy ID thực tế của người dùng

            boolean updateResult = UserDAO.adjustUser(selectedUser);

            if (updateResult) {
                userListTV.getItems().set(selectedIndex, selectedUser);
                System.out.println("Update successful!");
                welcomeMsg.setText("Update successful!");
            } else {
                System.out.println("Error updating database!");
                welcomeMsg.setText("Error updating database!");
            }
        } else {
            showAlert("No selection", "No User Selected", "Please select a user in the table.");
        }
    }


    
    @FXML
    public void onClickDelete() {
        int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User selectedUser = userListTV.getItems().get(selectedIndex);
            boolean deleteResult = UserDAO.deleteUser(selectedUser.getId()); // Truyền ID của người dùng

            if (deleteResult) {
                userListTV.getItems().remove(selectedIndex);
                System.out.println("Delete successful!");
                welcomeMsg.setText("Delete successful!");
            } else {
                System.out.println("Error deleting from database!");
                welcomeMsg.setText("Error deleting from database!");
            }
        } else {
            showAlert("No selection", "No User Selected", "Please select a user in the table.");
        }
    }

    
    
//    @FXML
//    public void onClickDelete() {
//        int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
//        if (selectedIndex >= 0) {
//            User selectedUser = userListTV.getItems().get(selectedIndex);
//            boolean deleteResult = UserDAO.deleteUser(selectedUser);
//
//            if (deleteResult) {
//                userListTV.getItems().remove(selectedIndex);
//                System.out.println("Delete successful!");
//                welcomeMsg.setText("Delete successful!");
//            } else {
//                System.out.println("Error deleting from database!");
//                welcomeMsg.setText("Error deleting from database!");
//            }
//        } else {
//            showAlert("No selection", "No User Selected", "Please select a user in the table.");
//        }
//    }

//    @FXML
//    public void onClickDelete() {
//        int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
//        if (selectedIndex >= 0) {
//            User selectedUser = userListTV.getItems().get(selectedIndex);
//            int userId = selectedUser.getId();  // Lấy ID của người dùng
//            boolean deleteResult = UserDAO.deleteUser(userId);  // Truyền ID vào phương thức deleteUser
//
//            if (deleteResult) {
//                userListTV.getItems().remove(selectedIndex);
//                System.out.println("Delete successful!");
//                welcomeMsg.setText("Delete successful!");
//            } else {
//                System.out.println("Error deleting from database!");
//                welcomeMsg.setText("Error deleting from database!");
//            }
//        } else {
//            showAlert("No selection", "No User Selected", "Please select a user in the table.");
//        }
//    }

    @FXML
    public void onClickExit() {
        welcomeMsg.getScene().getWindow().hide();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public User getLoginedUser() {
        return loginedUser;
    }

    public void setLoginedUser(User loginedUser) {
        this.loginedUser = loginedUser;
    }
}
