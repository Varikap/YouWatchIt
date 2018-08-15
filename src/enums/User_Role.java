package enums;

public enum User_Role {
	ADMINISTRATOR,
	USER;
	
	public static User_Role getRole(int x) {
		switch(x) {
		case 1:
			return ADMINISTRATOR;
			default:
				return USER;
				
		}
	}
	
	public static int getUsersRoleInt(User_Role role) {
		switch(role) {
		case ADMINISTRATOR:
			return 1;
		default:
			return 0;
		}
	}
}
