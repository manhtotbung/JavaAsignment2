package register.fxml;

import java.awt.TextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController{
	@FXML 
	private Label welcomeMsg;
	
	@FXML
	private void OnClickExit() {
		//từ thành phần con dò ra (cửa cổ)
		welcomeMsg.getScene().getWindow().hide();
	}
}
