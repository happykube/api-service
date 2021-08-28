package com.api.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/*
 Swagger url: http://{host}:{port}/swagger-ui/ 
 */

@RestController
@RequestMapping("v1")
public class ApiController {
	private Map<String, User> userList;
	

	@PostConstruct
	private void init() {
		userList = new HashMap<>();
		
		User user = User.builder()
				.userId("user01")
				.userName("홍길동")
				.build();
		
		userList.put(user.getUserId(), user);

		user = User.builder()
				.userId("user02")
				.userName("이해경")
				.build();
		userList.put(user.getUserId(), user);
		
	}
	
	@GetMapping("users")
	@ApiOperation(value="사용자 정보 목록", notes="사용자 정보 목록을 반환합니다. ")
	public ResponseEntity<Result<List<User>>> getUsers() {
		List<User> lists = userList
				.values()
				.stream()
				.collect(Collectors.toCollection(ArrayList::new));
		
		return new ResponseEntity<Result<List<User>>>(Result.<List<User>>builder()
				.returnCode(true)
				.result(lists)
				.build(), HttpStatus.OK);
		
	}
	
	@GetMapping("users/{userId}")
	@ApiOperation(value = "사용자 조회", notes = "사용자 정보를 조회합니다. ")
	public ResponseEntity<Result<User>> get(
		@ApiParam(
			name="userId",
			type="String",
			value="사용자ID",
			example="user01",
			required=true
		) @PathVariable String userId)
	{
		Result <User> result = null;
		User user = userList.get(userId);
		if (user != null) {
			result = Result.<User>builder()
					.returnCode(true)
					.result(user)
					.build();
			
		} else {
			result = Result.<User>builder()
					.returnCode(false)
					.returnMessage("ID가 [" + userId+"]인 사용자를 찾을 수 없습니다.")
					.build();
					
		}
		
		return new ResponseEntity<Result<User>>(result, HttpStatus.OK);
	}
	
	@PostMapping("users")
	@ApiOperation(value = "사용자 등록", notes="새로운 사용자를 등록합니다.")
	public ResponseEntity<Result<Void>> addUser(
		@ApiParam(
			name = "user",
			type = "User",
			value = "사용자 정보",
			required = true
		) @RequestBody User user)
	{
		userList.put(user.getUserId(), user);
		
		Result<Void> result = Result.<Void>builder() 
				.returnCode(true)
				.returnMessage("ID가 [" + user.getUserId()+"]인 사용자를 등록하였습니다.")
				.build();
		
		return new ResponseEntity<Result<Void>>(result, HttpStatus.OK);
	}
	
	@PutMapping("users/{userId}/{userName}")
	@ApiOperation(value = "사용자정보 변경", notes="사용자 정보를 변경합니다. ")
	public ResponseEntity<Result<Void>> updateUser(
		@ApiParam(
			name = "userId",
			type = "String",
			value = "사용자 ID",
			required = true
		) @PathVariable String userId, 
		
		@ApiParam(
			name = "userName",
			type = "String",
			value = "사용자 이름",
			required = true
		) @PathVariable String userName)		
	{
		Result<Void> result = null;
		User user = userList.get(userId);
		
		if(user != null) {
			userList.put(userId, User.builder()
					.userId(userId)
					.userName(userName)
					.build());
			
			result = Result.<Void>builder() 
					.returnCode(true)
					.returnMessage("변경을 완료하였습니다 ")
					.build(); 
		} else {
			result = Result.<Void>builder() 
					.returnCode(false)
					.returnMessage("ID가 [" + userId+"]인 사용자를 찾을 수 없습니다.")
					.build(); 
		}
		
		return new ResponseEntity<Result<Void>>(result, HttpStatus.OK);
		
	}
	
	@DeleteMapping("users/{userId}")
	@ApiOperation(value = "사용자 삭제", notes = "지정된 사용자 정보를 삭제합니다. ")
	public ResponseEntity<Result<Void>> deleteUser(
		@ApiParam(
			name = "userId",
			type = "String",
			value = "사용자 ID",
			required = true
		) @PathVariable String userId)
	{
		Result<Void> result = null;
		
		if(userList.remove(userId) != null) {
			result = Result.<Void>builder() 
					.returnCode(true)
					.returnMessage("삭제를 완료하였습니다. ")
					.build();
		} else {
			result = Result.<Void>builder() 
					.returnCode(false)
					.returnMessage("ID가 [" + userId+"]인 사용자를 찾을 수 없습니다.")
					.build(); 
		}
		
		return new ResponseEntity<Result<Void>>(result, HttpStatus.OK);
	}
}
