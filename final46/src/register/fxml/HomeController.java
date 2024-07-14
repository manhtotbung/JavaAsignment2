package register.fxml;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class HomeController implements Initializable {

    @FXML
    private TableView<Book> userListTV;

    @FXML
    private TableColumn<Book, String> maSachCol;

    @FXML
    private TableColumn<Book, String> tenSachCol;

    @FXML
    private TableColumn<Book, String> giaSachCol;

    @FXML
    private TextField maSachTF;

    @FXML
    private TextField tenSachTF;

    @FXML
    private TextField giaSachTF;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnAdjust;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnImg;

    @FXML
    private Label welcomeMsg;

    @FXML
    private ImageView imgView;

    // New attributes for role and userName
    private int role;
    private String userName;

    private Book loginedUser;
    private ObservableList<Book> obsList;
    private String img;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Run later to ensure role and userName values are received
        Platform.runLater(() -> {
            // Welcome message update
            welcomeMsg.setText("Xin chào " + loginedUser.getFullname());
            System.out.println("Logged in user role: " + loginedUser.getRole());
           
            // Role-based UI adjustments
            if (loginedUser.getRole() == 0) { // 0 là khách
            	btnAdd.setManaged(false);
				btnAdd.setVisible(false);
				btnAdjust.setManaged(false);
				btnAdjust.setVisible(false);
				btnDelete.setManaged(false);
				btnDelete.setVisible(false);
				btnImg.setManaged(false);
				btnImg.setVisible(false);
				
                welcomeMsg.setText("Login nhân viên:\n" + loginedUser.getFullname());
            } else { // If lecturer
                welcomeMsg.setText("Login quản lý:\n" + loginedUser.getFullname());
            }
        });
        // Set TableView columns
        maSachCol.setCellValueFactory(new PropertyValueFactory<Book, String>("maSach"));
        tenSachCol.setCellValueFactory(new PropertyValueFactory<Book, String>("tenSach"));
        giaSachCol.setCellValueFactory(new PropertyValueFactory<Book, String>("giaSach"));

        // Fetch user list and set it to TableView
        List<Book> listUser = UserDAO.listAllUser();
        obsList = FXCollections.observableArrayList(listUser);
        userListTV.setItems(obsList);
    }

    @FXML
    public void onClickRow() {
        Book selectedUser = userListTV.getSelectionModel().getSelectedItem();
        maSachTF.setText(selectedUser.getMaSach());
        tenSachTF.setText(selectedUser.getTenSach());
        giaSachTF.setText(selectedUser.getGiaSach());
        // Load image into ImageView
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
//
//    @FXML
//    private void onClickImg() {
//        // Lấy Stage hiện tại từ button
//        Stage stage = (Stage) btnImg.getScene().getWindow();
//
//        final FileChooser fileChooser = new FileChooser();
//
//        // Tạo bộ lọc tệp (tuỳ chọn)
//        fileChooser.getExtensionFilters().addAll(
//            new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".img"),
//            new FileChooser.ExtensionFilter("All Files", ".*")
//        );
//
//        // Mở hộp thoại chọn tệp
//        File file = fileChooser.showOpenDialog(stage);
//        if (file != null) {
//            // Lấy đường dẫn tệp và hiển thị trong ImgView
//            Image image = new Image(file.toURI().toString());
//            imgView.setImage(image);
//            img = file.getAbsolutePath();
//
//
//        }
//     }
    @FXML
    private void onClickImg() {
        // Lấy Stage hiện tại từ button
        Stage stage = (Stage) btnImg.getScene().getWindow();

        final FileChooser fileChooser = new FileChooser();

        // Tạo bộ lọc tệp (tuỳ chọn)
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Mở hộp thoại chọn tệp
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            // Lấy đường dẫn tệp và hiển thị trong ImgView
            Image image = new Image(file.toURI().toString());
            imgView.setImage(image);
            img = file.getAbsolutePath();
        }
    }

    @FXML
    public void onClickAdd() {
      
            Book sach = new Book(maSachTF.getText(), tenSachTF.getText(), giaSachTF.getText());
            boolean addResult = UserDAO.addUser(sach);

            if (addResult) {
                userListTV.getItems().add(sach);
                System.out.println("Add successful!");
                welcomeMsg.setText("Add successful!");
            } else {
                System.out.println("Error adding to database!");
                welcomeMsg.setText("Error adding to database!");
            }
        }
    

//    @FXML
//    public void onClickAdjust() {
//       
//            int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
//            if (selectedIndex >= 0) {
//                Book selectedUser = userListTV.getItems().get(selectedIndex);
//                selectedUser.setMaSach(maSachTF.getText());
//                selectedUser.setTenSach(tenSachTF.getText());
//                selectedUser.setGiaSach(giaSachTF.getText());
//                selectedUser.setId(selectedUser.getId());
//                selectedUser.setImgPath(img);
//
//                boolean updateResult = UserDAO.adjustUser(selectedUser);
//
//                if (updateResult) {
//                    userListTV.getItems().set(selectedIndex, selectedUser);
//                    System.out.println("Update successful!");
//                    welcomeMsg.setText("Update successful!");
//                } else {
//                    System.out.println("Error updating database!");
//                    welcomeMsg.setText("Error updating database!");
//                }
//            } else {
//                showAlert("No selection", "No User Selected", "Please select a user in the table.");
//            }
//        }
    
    @FXML
    public void onClickAdjust() {
        int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Book selectedUser = userListTV.getItems().get(selectedIndex);
            selectedUser.setMaSach(maSachTF.getText());
            selectedUser.setTenSach(tenSachTF.getText());
            selectedUser.setGiaSach(giaSachTF.getText());
            selectedUser.setId(selectedUser.getId());
            selectedUser.setImgPath(img);

            System.out.println("Adjusting user: " + selectedUser);

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
                Book selectedUser = userListTV.getItems().get(selectedIndex);
                selectedUser.setId(selectedIndex);

                boolean deleteResult = UserDAO.deleteUser(selectedUser.getId());

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

    public Book getLoginedUser() {
        return loginedUser;
    }

    public void setLoginedUser(Book loginedUser) {
        this.loginedUser = loginedUser;
    }

    // Getter and setter for role and userName
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
   
}
