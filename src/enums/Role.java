package enums;

public enum Role {
	USER("User"), ADMIN("Admin");
	
	private String label;
	
	private Role(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}
