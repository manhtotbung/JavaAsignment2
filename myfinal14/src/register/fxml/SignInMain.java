package register.fxml;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SignInMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			 // Đọc file fxml và vẽ giao diện.
	         Parent root = FXMLLoader.load(getClass()
	                   .getResource("SignInGUI.fxml"));
			
			// Thêm layout vào Scene
			Scene scene = new Scene(root);
			
			// Thêm Scene vào Stage
			primaryStage.setScene(scene);
			
			// Hiển thị Stage
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
