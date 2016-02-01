package com.papoye.UserMangement.domain;

public enum Role {
	NORMAL_USER("normal_user"), ADMIN("admin");
	private final String name;

	private Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
