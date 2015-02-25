package core;

public class PersonMain {
	static Person p;
	
	public static void main(String[] args) {
		p = new Person("Ca Odden", "odden", "caroliod@stud.ntnu.no", 91620084, "987de543");
		
		System.out.println(p.getEmail());
	}

}
