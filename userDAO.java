package register.fxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {
	public static User checkUser(String email) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";

		User user = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			String sql = "SELECT * from tbluser WHERE email = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			// để trỏ qua câu lệnh tiếp theo

			if (rs.next()) {
				user = new User(email, rs.getString("fullname"));
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("không kết nối database");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("không kết nối database 2");
		}
		return user;
	}

	public static boolean addUser(User user) {
		String jbdcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";

		boolean rowInserted = false;
		try {
			Class.forName("net.ucanaccess.jbdc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(jbdcURL, jdbcUser, jdbcPass);
			String sql = "INSERT INTO tbluser (email, fullname) values(?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getHoTen());

			rowInserted = statement.executeUpdate() > 0;
			statement.close();
			connection.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowInserted;

	}

}