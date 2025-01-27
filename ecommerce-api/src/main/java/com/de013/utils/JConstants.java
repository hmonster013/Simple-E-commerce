package com.de013.utils;

public class JConstants {
    public static final String PAGE = "1";
	public static final String SIZE = "10";
	public static final String DATA_LIST = "list";
	public static final String DATA_TOTAL = "total";
	public static final String DATA_SUMMARY = "summary";
	public static final String BEARER = "Bearer ";

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
}
