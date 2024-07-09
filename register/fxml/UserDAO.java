package register.fxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	
	public static List<User> listAllUser(){
		List<User> listUser = new ArrayList<>();
		
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			String sql = "Select * from tbluser";
			Statement ps = con.createStatement();
			
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()) {
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setFullname(rs.getString("fullname"));
				user.setImgPath(rs.getString("imgPath"));
				
				// Them vao danh sach
				listUser.add(user);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Ko thấy driver 1");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return listUser;
	}
	
	public static User checkUser(String email) {
		User user = null;
		
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			String sql = "Select * from tbluser Where email = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setEmail(email);
				user.setFullname(rs.getString("fullname"));
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Ko thấy driver 2");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return user;
	}


	public static boolean addUser(User user) {
	    String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
	    String jdbcUser = "";
	    String jdbcPass = "";
	    boolean rowInserted = false;
	    try {
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
	        String sql = "INSERT INTO tbluser (email, fullname) VALUES (?, ?)";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, user.getEmail());
	        statement.setString(2, user.getFullname());
	        rowInserted = statement.executeUpdate() > 0;
	        statement.close();
	        connection.close();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        System.out.println("Không thể thêm user. Lớp UcanaccessDriver không tìm thấy.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Không thể thêm user. Lỗi kết nối cơ sở dữ liệu.");
	    }
	    return rowInserted;
	}


	public static boolean adjustUser(User user) {
	    String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
	    String jdbcUser = "";
	    String jdbcPass = "";
	    boolean rowUpdated = false;
	    try {
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
	        String sql = "UPDATE tbluser SET email = ?, fullname = ? WHERE ID = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, user.getEmail());
	        statement.setString(2, user.getFullname());
	        statement.setInt(3, user.getId()); // Giả sử User có trường id
	        rowUpdated = statement.executeUpdate() > 0;
	        statement.close();
	        connection.close();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        System.out.println("Không thể cập nhật user. Lớp UcanaccessDriver không tìm thấy.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Không thể cập nhật user. Lỗi kết nối cơ sở dữ liệu.");
	    }
	    return rowUpdated;
	}

	public static boolean deleteUser(int user) {
	    String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
	    String jdbcUser = "";
	    String jdbcPass = "";
	    boolean rowDeleted = false;
	    try {
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
	        String sql = "DELETE FROM tbluser WHERE ID = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setInt(1, user);
	        rowDeleted = statement.executeUpdate() > 0;
	        statement.close();
	        connection.close();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        System.out.println("Không thể xóa user. Lớp UcanaccessDriver không tìm thấy.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Không thể xóa user. Lỗi kết nối cơ sở dữ liệu.");
	    }
	    return rowDeleted;
	}

	






}
