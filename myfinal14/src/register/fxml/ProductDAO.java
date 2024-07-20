package register.fxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final String JDBC_URL = "jdbc:ucanaccess://lib/QLNS.accdb";
    private static final String JDBC_USER = "";
    private static final String JDBC_PASS = "";

    static {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to load JDBC driver");
            e.printStackTrace();
        }
    }

    public static List<Book> listAllUser() {
        List<Book> listUser = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tbluser")) {

            while (rs.next()) {
                Book sach = new Book();
                sach.setMaSach(rs.getString("maSach"));
                sach.setTenSach(rs.getString("tenSach"));
                sach.setGiaSach(rs.getString("giaSach"));
                sach.setTenTacGia(rs.getString("tenTacGia"));
                sach.setSoLuong(rs.getString("soLuong"));
                sach.setId(rs.getInt("ID"));
                sach.setRole(rs.getInt("role"));
                sach.setImgPath(rs.getString("imgPath"));
                // Add to the list
                listUser.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection error.");
        }
        return listUser;
    }

    public static Boolean checkSP(String MSP) {
		
		String JDBC_URL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String JDBC_USER = "";
		String JDBC_PASS = "";
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
			String sql = "Select * from tbluser Where maSach = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, MSP);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Ko thấy driver");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return false;
	}

    
    public static Book checkUser(String email) {
        Book user = null;
        String sql = "SELECT * FROM tbluser WHERE email = ?";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new Book();
                    user.setEmail(email);
                    user.setPassword(rs.getString("password"));
                    user.setFullname(rs.getString("fullname"));
                    user.setRole(rs.getInt("role"));

                    System.out.println("User found: " + user.getFullname() + ", Role: " + user.getRole()); 
                } else {
                    System.out.println("User not found with email: " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection error.");
        }
        return user;
    }

    public static boolean addSP(Book SP) {
    	String JDBC_URL = "jdbc:ucanaccess://lib/QLNS.accdb";
  	    String JDBC_USER = "";
  	    String JDBC_PASS = "";

	    boolean rowInserted = false;
	    try {
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

	        Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
			String sql = "INSERT INTO tbluser (maSach, tenSach, giaSach,tenTacGia, soLuong, imgPath) VALUES (?, ?, ?, ?, ? ,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, SP.getMaSach());
			stmt.setString(2, SP.getTenSach());
			stmt.setString(3, SP.getGiaSach());
			stmt.setString(4, SP.getTenTacGia());
			stmt.setString(5, SP.getSoLuong());
			
			stmt.setString(6, SP.getImgPath());
	        
	        rowInserted = stmt.executeUpdate() > 0;

	        stmt.close();
	        con.close();

	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return rowInserted;
	}
	

    public static boolean adjustSP(Book SP) {
	    String JDBC_URL = "jdbc:ucanaccess://lib/QLNS.accdb";
	    String JDBC_USER = "";
	    String JDBC_PASS = "";

	    boolean rowUpdated = false;
	    try {
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

	        Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            PreparedStatement stmt = con.prepareStatement("UPDATE tbluser SET maSach = ?, tenSach = ?, giaSach = ?, tenTacGia = ?, soLuong = ?, imgPath = ? WHERE ID = ?");

          stmt.setString(1, SP.getMaSach());
          stmt.setString(2, SP.getTenSach());
          stmt.setString(3, SP.getGiaSach());
          stmt.setString(4, SP.getTenTacGia());
          stmt.setString(5, SP.getSoLuong());
          stmt.setString(6, SP.getImgPath());
          stmt.setInt(7, SP.getId());

            rowUpdated = stmt.executeUpdate() > 0;
	        

	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace(); 
	        System.out.println("không thể update");
	    }
	    return rowUpdated;
	}

    
    public static boolean deleteSP(String maSach) {
        String sql = "DELETE FROM tbluser WHERE maSach = ?";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = con.prepareStatement(sql)) {

        	stmt.setString(1, maSach);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to delete user from the database.");
            return false;
        }
    }
}
