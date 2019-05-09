package ujs.mlearn.entity;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Teacher {
	public static int LOGIN = 1;
	public static int LOGOUT = 0;

	private int userId;

	private String tId;

	private String name;

	private String password;

	private String sex;

	private String email;

	private String phone;

	private String logintime;

	private String photo;
	
	private int islogin;
	
	private String college;
	
	private String specialty;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		if (logintime == null || logintime.equals("")) {
			long currentTimeMillis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(currentTimeMillis);
			Time time = new Time(currentTimeMillis);
			this.logintime = date.toString() + " " + time.toString();
		} else {
			this.logintime = logintime;
		}
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public int getIslogin() {
		return islogin;
	}

	public void setIslogin(int islogin) {
		this.islogin = islogin;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public Teacher() {
		super();
	}

	
	
	

	public Teacher(int userId, String tId, String password, String name, String sex, String phone, String email,
			String logintime) {
		super();
		this.userId = userId;
		this.tId = tId;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.email = email;
		this.phone = phone;
		if (logintime == null || logintime.equals("")) {
			long currentTimeMillis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(currentTimeMillis);
			Time time = new Time(currentTimeMillis);
			this.logintime = date.toString() + " " + time.toString();
		} else {
			this.logintime = logintime;
		}
	}

	public Teacher(int userId, String tId, String password, String name, String sex, String phone, String email,
			String photo, String logintime) {
		super();
		this.userId = userId;
		this.tId = tId;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.email = email;
		this.phone = phone;
		this.photo = photo;
		if (logintime == null || logintime.equals("")) {
			long cTime = System.currentTimeMillis();
			Date upDate = new Date(cTime);
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			this.logintime = fmt.format(upDate);
		} else {
			this.logintime = logintime;
		}
	}

	public Teacher(String userId2, String userName, String userPass1, String userSex, String userEmail, String userPhone,
			String loginTime2, String photoURL, String userCollege, String userSpecialty,int isLogin) {
		super();
		this.tId = userId2;
		this.name = userName;
		this.password = userPass1;
		this.sex = userSex;
		this.email = userEmail;
		this.phone = userPhone;
		this.logintime = loginTime2;
		this.photo = photoURL;
		this.college = userCollege;
		this.specialty = userSpecialty;
		this.islogin=isLogin;
	}

	@Override
	public String toString() {
		return "Teacher [userId=" + userId + ", tId=" + tId + ", password=" + password + ", name=" + name + ", sex="
				+ sex + ", phone=" + phone + ", email=" + email + ", logintime=" + logintime + ", college="+college
				+", specialty="+specialty+"]";
	}

}
