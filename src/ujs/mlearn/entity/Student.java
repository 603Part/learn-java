package ujs.mlearn.entity;

import java.sql.Time;

public class Student {
	public static int LOGIN = 1;
	public static int LOGOUT = 0;

	private int userId;

	private String sId;

	private String name;

	private String password;

	private String sex;

	private String email;

	private String phone;

	private String photo;

	private String signature;

	private String logintime;
	
	private int islogin;
	
	private String college;
	
	private String specialty;

	public Student() {
		super();
	}

	public Student(int userId, String sId, String password, String name, String sex, String phone, String email,
			String photo, String signature, String logintime) {
		super();
		this.userId = userId;
		this.sId = sId;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.phone = phone;
		this.email = email;
		this.photo = photo;
		this.signature = signature;
		if (logintime == null || logintime.equals("")) {
			long currentTimeMillis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(currentTimeMillis);
			Time time = new Time(currentTimeMillis);
			this.logintime = date.toString() + " " + time.toString();
		} else {
			this.logintime = logintime;
		}
		this.islogin = Student.LOGOUT;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	@Override
	public String toString() {
		return "Student [userId=" + userId + ", sId=" + sId + ", name=" + name + ", password=" + password + ", sex="
				+ sex + ", phone=" + phone + ", email=" + email + ", photo=" + photo + ", signature=" + signature
				+ ", logintime=" + logintime + ", college="+college+", specialty="+specialty+"]";
	}

}
