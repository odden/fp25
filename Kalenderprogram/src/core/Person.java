package core;

public class Person {
	
	private String name;
	private String username;
	private String email;
	private int phoneNumber;
	private String password;
	
	//Constructor
	public Person(String name, String username, String email, int phoneNumber, String password){
		setName(name);
		setUsername(username);
		setEmail(email);
		setPhoneNumber(phoneNumber);
		setPassword(password);
	}
	
	//Navn
	public String getName(){
		return name;
	}
	public void setName(String name) {
		if (name.matches("[A-Z][a-z]+ [A-Z][a-z]+")){
			this.name = name;
		}else{
			this.name = "Ugyldig";
		}
	}

	//Brukernavn
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		if (username.matches("([a-z]+[a-z,0-9]){3,}")){
			this.username = username;
		}else{
			this.username = "Ugyldig";
		}
	}
	
	//Epostadresse
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(isValidEmailAddress(email)){
			this.email = email;	
		}else{
			this.email = "Ugyldig";
		}
	}
	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	//Telefonnr
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		if(Integer.toString(phoneNumber).length() == 8){
			this.phoneNumber = phoneNumber;
		}else{
			this.phoneNumber = 0;
		}
	}
	
	//Passord
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		if (password.matches("[A-Z,a-z,0-9]{6,}")){
			this.password = password;
		}else{
			this.password = "Ugyldig";
		}
	}
	
}
