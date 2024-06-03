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
	TextField name;
	@FXML
	TextField email;
	@FXML
	Label msg;

	@FXML
	public void onClickRegisterButton() {
		System.out.println("email" + email.getText());
		System.out.println("name" + name.getText());

		User user = userDAO.checkUser(email.getText());

		if (user != null) {
			msg.setText("cant sign up, your email was existed");
			System.out.println(user.toString());
		} else {

//			// add vao csdl
//			userDAO.addUser(new User(email.getText(), name.getText()));
//			// đóng cửa sổ hiện tại
			email.getScene().getWindow().hide();
//			// mở cứa sổ home
			
			goHomeScreen();

		}

	}

	private void goHomeScreen() {

		try {
			Parent root = FXMLLoader.load(getClass()
					.getResource("HomeGUI.fxml"));
			
			//thêm layout vào scene
			Scene scene = new Scene(root);
			
			//thêm scene vào stage
			Stage homeStage = new Stage();
			homeStage.setScene(scene);
			homeStage.setTitle("Home");

			// hiển thị stage
			homeStage.show();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("không hiển thị menu đượccccc");
		}

	}

}
