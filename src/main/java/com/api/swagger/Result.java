package com.api.swagger;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Result<T> {
	private boolean returnCode;
	private String returnMessage;
	private T result;
}
