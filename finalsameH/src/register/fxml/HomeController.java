package register.fxml;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.TextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
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
    private TableColumn<Book, String> tenTacGiaCol;
    
    @FXML
    private TableColumn<Book, String> soLuongCol;

    @FXML
    private TextField maSachTF;

    @FXML
    private TextField tenSachTF;

    @FXML
    private TextField giaSachTF;
    
    @FXML
    private TextField tenTacGiaTF;
    
    @FXML
    private TextField soLuongTF;
    
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
        tenTacGiaCol.setCellValueFactory(new PropertyValueFactory<Book, String>("tenTacGia"));
        soLuongCol.setCellValueFactory(new PropertyValueFactory<Book, String>("soLuong"));

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
        tenTacGiaTF.setText(selectedUser.getTenTacGia());
        soLuongTF.setText(selectedUser.getSoLuong());
        
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
            System.out.println("Image selected: " + img); 
        }
    }

 // Xử lý sự kiện bấm nút Add sau khi nhập dữ liệu vào 3 ô
 	
 	public void onClickAdd() {
 		if(UserDAO.checkSP(maSachTF.getText())==true) {
 			welcomeMsg.setText("Đã có mã sản phẩm này rồi !");
 		}
 		else {
 			 // Đường dẫn tệp nguồn
 	        Path sourcePath = Paths.get(img);
 	        
 	        // Đường dẫn tệp đích
 	        Path targetDir = Paths.get("img");
 	        Path targetPath = targetDir.resolve(sourcePath.getFileName().toString());
 	        
 			// Tạo đối tượng sinh viên từ 3 ô dữ liệu nhập vào
 			try {
 				Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			img=targetPath.toString();
 			Book sp = new Book(maSachTF.getText(), tenSachTF.getText(), giaSachTF.getText(),tenTacGiaTF.getText(), soLuongTF.getText(), img);
 	    
 			// Thêm vào danh sách dữ liệu của tableview
 			userListTV.getItems().add(sp);
 			//Them du lieu vao database
 			UserDAO.addUser(sp);
 			welcomeMsg.setText("Thêm sản phẩm thành công");
 		}
 	}
    
//    @FXML
//    public void onClickAdjust() {
//        int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
//        if (selectedIndex >= 0) {
//            Book selectedUser = userListTV.getItems().get(selectedIndex);
//            selectedUser.setMaSach(maSachTF.getText());
//            selectedUser.setTenSach(tenSachTF.getText());
//            selectedUser.setGiaSach(giaSachTF.getText());
//            selectedUser.setTenTacGia(tenTacGiaTF.getText());
//            selectedUser.setSoLuong(soLuongTF.getText());
//            selectedUser.setId(selectedIndex);
//
//            if (img != null && !img.isEmpty()) {
//                // Đổi tên ảnh để tránh ghi đè
//                String newImgName = "new_" + System.currentTimeMillis() + "_" + Paths.get(img).getFileName().toString();
//                Path sourcePath = Paths.get(img);
//                Path targetDir = Paths.get("C:\\Users\\dophu\\Documents\\java\\final50\\img");
//                Path targetPath = targetDir.resolve(newImgName);
//
//                try {
//                    if (Files.exists(targetPath)) {
//                        
//                        System.out.println("ảnh đã tồn tại: " + targetPath.toString());
//                    } else {
//                        // Sao chép ảnh vào thư mục đích
//                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
//                    }
//                    selectedUser.setImgPath(targetPath.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    System.out.println("Error copying image to target directory.");
//                    welcomeMsg.setText("Error copying image to target directory.");
//                    return;
//                }
//            }
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
 	
 	public void onClickAdjust() {
		if(UserDAO.checkSP(maSachTF.getText())==true) {
			// Đường dẫn tệp nguồn
	        Path sourcePath = Paths.get(img);
	        
	        // Đường dẫn tệp đích
	        Path targetDir = Paths.get("C:\\Users\\dophu\\Documents\\java\\final16\\img");
	        Path targetPath = targetDir.resolve(sourcePath.getFileName().toString());
	        
			// Tạo đối tượng sinh viên từ 3 ô dữ liệu nhập vào
			try {
				Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			img=targetPath.toString();
			// Lấy về chỉ số dòng đang được chọn trên bảng
			int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();
			// Lấy về đối tượng dữ liệu tương ứng với dòng được chọn
			Book selectedSP = userListTV.getItems().get(selectedIndex);
	
			// Thay đổi dữ liệu trong đối tượng sinh viên được chọn
			selectedSP = new Book(maSachTF.getText(), tenSachTF.getText(), giaSachTF.getText(),tenTacGiaTF.getText(), soLuongTF.getText(), img);
			// Cập nhật lại đối tượng dữ liệu tại vị trí được chọn để hiển thị lên bảng
	
			userListTV.getItems().set(selectedIndex, selectedSP);
			
			//Thay doi du lieu trong database
			UserDAO.adjustUser(selectedSP);
			welcomeMsg.setText("Sửa thành công");
			System.out.println("Sửa thành công");
			return;
		}
		welcomeMsg.setText("Không có mã sản phẩm này !");
		System.out.println("Không có mã sản phẩm này !");
	}
    
    @FXML
    public void onClickDelete() {
        int selectedIndex = userListTV.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            // Hiển thị hộp thoại xác nhận
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText("Bạn chắc chắn muốn xóa sản phẩm này?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Get the selected book
                Book selectedBook = userListTV.getItems().get(selectedIndex);

                // Clear the ImageView to release the file handle
                imgView.setImage(null);

                // Force garbage collection
                System.gc();
                try {
                    // Wait for a short period
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Delete the image file
                try {
                    Path imgPath = Paths.get(selectedBook.getImgPath());
                    if (Files.exists(imgPath)) {
                        Files.delete(imgPath);
                        System.out.println("Image deleted: " + imgPath.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error deleting image file.");
                    welcomeMsg.setText("Error deleting image file.");
                    return;
                }

                // Remove the book from the database
                boolean deleteResult = UserDAO.deleteUser(selectedBook.getMaSach());

                if (deleteResult) {
                    // Remove the item from the TableView
                    userListTV.getItems().remove(selectedIndex);
                    System.out.println("Đã xóa thành công!");
                    welcomeMsg.setText("Đã xóa thành công!");
                } else {
                    System.out.println("xóa thất bại.");
                    welcomeMsg.setText("xóa thất bại.");
                }
            } else {
                System.out.println("Đã hủy xóa!");
            }
        } else {
            // Hiện hộp thoại cảnh báo
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Không có dòng nào được chọn");
            alert.setHeaderText("Không thể xóa sản phẩm");
            alert.setContentText("Vui lòng chọn một dòng để xóa.");
            alert.showAndWait();
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
