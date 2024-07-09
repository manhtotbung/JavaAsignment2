package register.fxml;

public class Book {
	private int id;
	private String maSach;
	private String tenSach;
	
	
	private String giaSach;
	
	private String imgPath;
	
	public Book() {}
	
	public Book(String maSach, String tenSach, String giaSach) {
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.giaSach = giaSach;
	}
//	    Các getter và setter cho id, email, fullname và imgPath
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
	
	
	
}
