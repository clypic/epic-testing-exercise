package cucumber.lib;

public class User {
	
	String name = null;
	String pass = null;
	String msisdn = null;
	Boolean is_valid = null;

	public User(String name, String pass, String msisdn, Boolean is_valid) {
		this.name = name;
		this.pass = pass;
		this.msisdn = msisdn;
		this.is_valid = is_valid;
	}

	public String getName() {
		return this.name;
	}

	public String getPass() {
		return this.pass;
	}

	public Boolean getIsValid() {
		return this.is_valid;
	}
}