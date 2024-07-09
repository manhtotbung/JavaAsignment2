package register.fxml;

import java.util.List;

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
	private TextField emailTf;
	
	@FXML
	private TextField fullnameTf;
	
	@FXML
	public void onClickRegisBtn() {
		System.out.println("Email: " + emailTf.getText());
		System.out.println("Fullname: " + fullnameTf.getText());
		
		// Lấy user ứng với email trong csdl
		User user = UserDAO.checkUser(emailTf.getText());
		if(user != null) {
			errorL.setText("Lỗi email đã tồn tại");
			// Thử in thông tin lấy được
			System.out.println(user.toString());
		}else {
			// Thêm vào CSDL user mới
//			UserDAO.addUser(user);
			
			// Đóng cửa sổ đăng ký
			emailTf.getScene().getWindow().hide();
			
			// Mở ra cửa sổ Home và truyền thông tin User đăng ký cho cửa sổ Home
			goHomeScreen(new User(emailTf.getText(), fullnameTf.getText()));
		}
	}
	
	public void goHomeScreen(User loginedUser) {
		try {
			 FXMLLoader fxmlLoader = new FXMLLoader(getClass()
	                   .getResource("HomeGUI.fxml"));	
			
			 // Đọc file fxml và vẽ giao diện.
	         Parent root = fxmlLoader.load();
	         
	         // Lấy ra HomeController để truyền dữ liệu vào
	         HomeController hc = fxmlLoader.getController();
	         hc.setLoginedUser(loginedUser);
			
			// Thêm layout vào Scene
			Scene scene = new Scene(root);
			
			// Thêm Scene vào Stage
			Stage homeStage = new Stage();
			homeStage.setScene(scene);
			
			// Hiển thị Stage
			homeStage.setTitle("Home");
			homeStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
