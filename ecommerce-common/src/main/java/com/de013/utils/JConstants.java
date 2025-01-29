package com.de013.utils;

public class JConstants {
    public static final String PAGE = "1";
	public static final String SIZE = "10";
	public static final String SIZE_MAX = "99999999";
	public static final String DATA_LIST = "list";
	public static final String DATA_TOTAL = "total";
	public static final String DATA_SUMMARY = "summary";
	public static final String BEARER = "Bearer ";

	// Date format
    public static final String ddMMyyyyHHmmss = "dd-MM-yyyy HH:mm:ss";
    public static final String YYYYMMDDHHmmss = "yyyy/MM/dd HH:mm:ss";
	public static final String DD_MM_YYYY = "dd-MM-yyyy";

	public enum Status {
		INIT, 
		ACTIVE, 
		INACTIVE, 
		VERIFY,
		UNVERIFY,
		BLOCK, 
		SUCCESS,
		BOT,
		ERROR
	}

	public enum OrderStatus {
		PENDING,
		PROCESSING,
		CONFIRM,
		SHIPPED,
		DELIVERED,
		CANCEL,
		RETURN,
		FAIL,
		SUCCESS
	}

	public enum CrudMethod {
		CREATE,
		UPDATE,
		DELETE
	}

	public enum ERole {
		ROLE_USER,
		ROLE_MODERATOR,
		ROLE_ADMIN
	}

	public enum EUserStatus {
		UNVERIFIED,
		ACTIVE,
		DELETED,
		BANNED
	}
	
	public enum OrderType {
		PURCHASE,
		SALES
	}

	public enum ReportType {
		SALES_COUNT,
		SALES_AMOUNT,
		SALES_REVENUA
	}
}
