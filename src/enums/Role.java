package enums;

public enum Role {
	ADMINISTRATOR,
	USER;
	
	public static Role getRole(int x) {
		switch(x) {
		case 1:
			return ADMINISTRATOR;
			default:
				return USER;
				
		}
	}
	
	public static int getUsersRoleInt(Role role) {
		switch(role) {
		case ADMINISTRATOR:
			return 1;
		default:
			return 0;
		}
	}
}
