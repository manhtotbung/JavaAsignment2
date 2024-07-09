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
import javafx.stage.FileChooser;

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
    private Label welcomeMsg;

    @FXML
    private ImageView imgView;

    private Book loginedUser;
    private ObservableList<Book> obsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    	
    	
    	// Nội dung khi mở màn hình Home
        Platform.runLater(() -> {
            welcomeMsg.setText("Xin chào " + loginedUser.getTenSach());
        });

        // Bắt cặp
        maSachCol.setCellValueFactory(new PropertyValueFactory<Book, String>("maSach"));
        tenSachCol.setCellValueFactory(new PropertyValueFactory<Book, String>("tenSach"));
        giaSachCol.setCellValueFactory(new PropertyValueFactory<Book, String>("giaSach"));

        // Lấy danh sách User từ CSDL
        List<Book> listUser = UserDAO.listAllUser();
        obsList = FXCollections.observableArrayList(listUser);
        // Đưa vào bảng TableView
        userListTV.setItems(obsList);
    }
    
    @FXML
    public void onClickRow() {
        Book selectedUser = userListTV.getSelectionModel().getSelectedItem();
        maSachTF.setText(selectedUser.getMaSach());
        tenSachTF.setText(selectedUser.getTenSach());
        giaSachTF.setText(selectedUser.getGiaSach());
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

    @FXML
    public void onClickAdjust() {
        int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Book selectedUser = userListTV.getItems().get(selectedIndex);
            selectedUser.setMaSach(maSachTF.getText());
            selectedUser.setTenSach(tenSachTF.getText());
            selectedUser.setGiaSach(giaSachTF.getText());
            
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
            Book selectedUser = userListTV.getItems().get(selectedIndex);
            selectedUser.setId(selectedIndex); 
            
            boolean deleteResult = UserDAO.deleteUser(selectedUser.getId()); // Truyền ID của người dùng

            if (deleteResult) {
                userListTV.getItems().remove(selectedIndex);
                System.out.println("Delete successful!");
                welcomeMsg.setText("Delete successful!");
            } else {
                System.out.println("Error deleting from database! 4");
                welcomeMsg.setText("Error deleting from database! 4");
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
}
