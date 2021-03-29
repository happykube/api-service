package com.api.swagger;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
	private String userId;
	private String userName;

}
