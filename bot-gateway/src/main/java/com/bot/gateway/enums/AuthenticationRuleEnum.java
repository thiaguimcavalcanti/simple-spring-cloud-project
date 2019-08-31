package com.bot.gateway.enums;

public enum AuthenticationRuleEnum {

	ROLE_ADMIN("ROLE_ADMIN", "authenticationRule.administrator"),
	ROLE_USER("ROLE_USER", "authenticationRule.user");

	private String ruleType;
	private String description;

	AuthenticationRuleEnum(String ruleType, String description) {
		this.ruleType = ruleType;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getRuleType() {
		return ruleType;
	}
}
