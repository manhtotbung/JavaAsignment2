package register.fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    @FXML
    private Label errorL;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField passwordTF;
    
	
	@SuppressWarnings("null")
	@FXML
	public void onClickRegisBtn() {
		System.out.println("Email: " + emailTF.getText());
		System.out.println("Pass: " + passwordTF.getText());
		
		// Lấy user ứng với email trong csdl
		Book user = UserDAO.checkUser(emailTF.getText());
		if(user == null) {
			errorL.setText("TK chưa tồn tại");
			// Thử in thông tin lấy được
			System.out.println(user.toString());
		}else {
			
			// Đóng cửa sổ đăng ký
			emailTF.getScene().getWindow().hide();
			
			// Mở ra cửa sổ Home và truyền thông tin User đăng ký cho cửa sổ Home
			 goHomeScreen(new Book(user.getRole(), user.getFullname(),user.getEmail()));
		}
	}


    public void goHomeScreen(Book loginedUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeGUI.fxml"));

            // Đọc file fxml và vẽ giao diện.
            Parent root = fxmlLoader.load();

            // Lấy ra HomeController để truyền dữ liệu vào
            HomeController hc = fxmlLoader.getController();
            hc.setLoginedUser(loginedUser);
            System.out.println("Passed loginedUser with role: " + loginedUser.getRole()); // Log role

            // Thêm layout vào Scene
            Scene scene = new Scene(root);

            // Thêm Scene vào Stage
            Stage homeStage = new Stage();
            homeStage.setScene(scene);

            // Hiển thị Stage
            homeStage.setTitle("Home");
            homeStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Không thể hiển thị menu! 01");
        }
    }
}
