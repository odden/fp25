package core;

public class Person {
	
	private String name;
	private String username;
	private String email;
	private int phoneNumber;
	
	
	public void init(String name, String username, String email, int phoneNumber){
		this.name = name;
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(isValidEmailAddress(email)){
			this.email = email;	
		}
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}
	
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}

	public void setPhoneNumber(int phoneNumber) {
		if(Integer.toString(phoneNumber).length() == 8){
			this.phoneNumber = phoneNumber;
		}
	}

	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
