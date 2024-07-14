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

    public static boolean addUser(Book user) {
        String sql = "INSERT INTO tbluser (maSach, tenSach, giaSach, imgPath) VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, user.getMaSach());
            stmt.setString(2, user.getTenSach());
            stmt.setString(3, user.getGiaSach());
            
            stmt.setString(4, user.getImgPath());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to add user to the database.");
            return false;
        }
    }

//    public static boolean adjustUser(Book user) {
//        String sql = "UPDATE tbluser SET maSach = ?, tenSach = ?, giaSach = ?, imgPath = ? WHERE ID = ?";
//        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
//             PreparedStatement stmt = con.prepareStatement(sql)) {
//
//            stmt.setString(1, user.getMaSach());
//            stmt.setString(2, user.getTenSach());
//            stmt.setString(3, user.getGiaSach());
//            stmt.setString(4, user.getImgPath());
//            stmt.setInt(5, user.getId());
//            
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Unable to update user in the database.");
//            return false;
//        }
//    }
    
    public static boolean adjustUser(Book user) {
        String sql = "UPDATE tbluser SET maSach = ?, tenSach = ?, giaSach = ?, imgPath = ? WHERE ID = ?";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, user.getMaSach());
            stmt.setString(2, user.getTenSach());
            stmt.setString(3, user.getGiaSach());
            stmt.setString(4, user.getImgPath());
            stmt.setInt(5, user.getId());
            
            System.out.println("Executing query: " + sql);
            System.out.println("Parameters: " + user.getMaSach() + ", " + user.getTenSach() + ", " + user.getGiaSach() + ", " + user.getImgPath() + ", " + user.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to update user in the database.");
            return false;
        }
    }


    public static boolean deleteUser(int userId) {
        String sql = "DELETE FROM tbluser WHERE ID = ?";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to delete user from the database.");
            return false;
        }
    }
}
