package register.fxml;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class RegisterMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//đọc file fxml và vẽ giao diện
			Parent root = 
					FXMLLoader.load(getClass().getResource("Register.fxml"));
			
			//thêm layout vào scene
			Scene scene = new Scene(root);
			
			//thêm scene vào stage
			primaryStage.setScene(scene);
			
			//hiển thị state
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
