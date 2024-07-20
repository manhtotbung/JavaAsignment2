package register.fxml;

public class Book {
	private int id;
	private String fullname;
	private String password;
	private String maSach;
	private String tenSach;
	private String giaSach;
	private String tenTacGia;
	private String soLuong;
	
	private int role;
	
	
	private String imgPath;
	private String email;

	
	public Book() {}
	
	public Book(String maSach, String tenSach, String giaSach, String tenTacGia, String soLuong, int id) {
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.giaSach = giaSach;
		this.tenTacGia = tenTacGia;
		this.soLuong = soLuong;
		this.id = id;
		
	}
	 public Book(int role, String fullname, String email) {
	        this.role = role;
	        this.fullname = fullname;
	        this.email=email;
	    }

	    
	public Book(String maSach, String tenSach, String giaSach,String tenTacGia, String soLuong, String imgPath ) {
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.giaSach = giaSach;
		this.tenTacGia = tenTacGia;
		this.soLuong = soLuong;
		this.imgPath = imgPath;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	
	public String getMaSach() {
		return maSach;
	}

	public void setMaSach(String maSach) {
		this.maSach = maSach;
	}

	public String getTenSach() {
		return tenSach;
	}

	public void setTenSach(String tenSach) {
		this.tenSach = tenSach;
	}

	public String getGiaSach() {
		return giaSach;
	}

	public void setGiaSach(String giaSach) {
		this.giaSach = giaSach;
	}

	public String getTenTacGia() {
		return tenTacGia;
	}

	public void setTenTacGia(String tenTacGia) {
		this.tenTacGia = tenTacGia;
	}

	public String getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(String soLuong) {
		this.soLuong = soLuong;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return maSach + "-" + tenSach + "-" + giaSach ;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
}
