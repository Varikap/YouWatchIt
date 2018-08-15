package enums;

public enum Video_Comment {
	VIDEO,
	COMMENT;
	
	public static Video_Comment getVideoOrComment(int x) {
		switch (x) {
		case 1:
			return VIDEO;
		default:
			return COMMENT;
		}
	}
	
	public static int getVideoOrCommentId(Video_Comment videoOrComment) {
		switch (videoOrComment) {
		case VIDEO:
			return 1;
		default:
			return 0;
		}
	}
}
