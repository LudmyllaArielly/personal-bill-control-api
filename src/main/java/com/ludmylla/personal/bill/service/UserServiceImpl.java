package com.ludmylla.personal.bill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ludmylla.personal.bill.model.Token;
import com.ludmylla.personal.bill.model.UserAuthorize;
import com.ludmylla.personal.bill.model.dto.UserCreateDto;
import com.ludmylla.personal.bill.model.dto.UserLoginDto;
import com.ludmylla.personal.bill.model.dto.UserSearchDto;
import com.ludmylla.personal.bill.useful.Useful;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserAuthorizeService userAuthorizeService;
	
	
	private RestTemplate restTemplate = new RestTemplate();

	@Value("${com.user.api}")
	private String basePath;

	private Token receivedToken;

	private String userEmail;
	private String userRole;
	private String userEmailLogin;
	
	@Override
	public UserCreateDto create(UserCreateDto userCreateDto) {
		validationsUser(userCreateDto);
		getUserDataToSave(userCreateDto);
		String uri = basePath + "/user";
		ResponseEntity<UserCreateDto> request = restTemplate.postForEntity(uri, userCreateDto, UserCreateDto.class);
		UserCreateDto response = request.getBody();
		return response;
	}
	
	private void getUserDataToSave(UserCreateDto userCreateDto) {
		userEmail = userCreateDto.getEmail();
		userRole = userCreateDto.getRoles().toString();
		savesEmailAndUserRole();
	}
	
	private void savesEmailAndUserRole() {
		userAuthorizeService.save();
	}
	// usado pelo userAuthorization
	public String takesTheRoleOfTheuser() {
		String roleReceived = userRole;
		return roleReceived;
	}

	public String takesTheEmailOfTheuser() {
		String emailReceived = userEmail;
		return emailReceived;
	}
	
	public void releasesAuthorizationForTheUser() {
		String emailReceived = userEmailLogin;
		UserAuthorize findEmail = userAuthorizeService.findEmailByUser(emailReceived);
		
		if(findEmail != null) { 
			if(findEmail.getRole().equalsIgnoreCase("[USER]")) {
				throw new IllegalArgumentException("User not allowed!");
			}
		}else {
			throw new IllegalArgumentException("User is not registered!");
		}
	}

	@Override
	public Token login(UserLoginDto userLoginDto) {
		validationsUserLogin(userLoginDto);
		userEmailLogin = userLoginDto.getEmail();
		RestTemplate restTemplate = new RestTemplate();
		String uri = basePath + "/user/login";
		String loginJson = "{\"email\":" + addQuotes(userLoginDto.getEmail()) + ",\"password\":"
				+ addQuotes(userLoginDto.getPassword()) + "}";
		HttpEntity<String> httpEntity = new HttpEntity<>(loginJson, createJsonHeaders());
		ResponseEntity<Token> request = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Token.class);
		receivedToken = request.getBody();
		return request.getBody();
	}

	private String addQuotes(String value) {
		return new StringBuilder(300).append("\"").append(value).append("\"").toString();
	}

	private HttpHeaders createJsonHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@Override
	public UserSearchDto search(UserSearchDto userSearchDto) {
		validIfTokenIsNull();
		String insertToken = receivedToken.getToken();
	
		String uri = basePath + "/user";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + insertToken);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<UserSearchDto> request = restTemplate.exchange(uri, HttpMethod.GET, httpEntity,
				UserSearchDto.class);
		UserSearchDto response = request.getBody();

		return response;
	}

	private void validationsUser(UserCreateDto userCreateDto) {
		validIfAttributesUserIsNull(userCreateDto);
		validIfAttributesUserIsBlank(userCreateDto);
		validIfRolesIsNull(userCreateDto);
		validIfRolesIsEmpty(userCreateDto);
	}

	private void validationsUserLogin(UserLoginDto userLoginDto) {
		validIfEmailAndPasswordIsNull(userLoginDto);
		validIfEmailAndPasswordIsBlank(userLoginDto);
	}

	private void validIfAttributesUserIsBlank(UserCreateDto userCreateDto) {
		Useful.validIfAttributesAreBlank(userCreateDto.getCpf());
		Useful.validIfAttributesAreBlank(userCreateDto.getEmail());
		Useful.validIfAttributesAreBlank(userCreateDto.getName());
		Useful.validIfAttributesAreBlank(userCreateDto.getLastName());
		Useful.validIfAttributesAreBlank(userCreateDto.getPassword());
	}

	private void validIfAttributesUserIsNull(UserCreateDto userCreateDto) {
		Useful.validIfAttributesIsNull(userCreateDto.getCpf());
		Useful.validIfAttributesIsNull(userCreateDto.getEmail());
		Useful.validIfAttributesIsNull(userCreateDto.getName());
		Useful.validIfAttributesIsNull(userCreateDto.getLastName());
		Useful.validIfAttributesIsNull(userCreateDto.getPassword());
	}

	private void validIfEmailAndPasswordIsBlank(UserLoginDto userLoginDto) {
		Useful.validIfAttributesAreBlank(userLoginDto.getEmail());
		Useful.validIfAttributesAreBlank(userLoginDto.getPassword());
	}

	private void validIfEmailAndPasswordIsNull(UserLoginDto userLoginDto) {
		Useful.validIfAttributesIsNull(userLoginDto.getEmail());
		Useful.validIfAttributesIsNull(userLoginDto.getPassword());
	}

	public void validIfTokenIsNull() {
		boolean isTokenNull = receivedToken == null;
		if (isTokenNull) {
			throw new IllegalArgumentException("Not allowed, login!");
		}
	}

	private void validIfRolesIsEmpty(UserCreateDto userCreateDto) {
		boolean isRolesEmpty = userCreateDto.getRoles().isEmpty();
		if (isRolesEmpty) {
			throw new IllegalArgumentException("Roles cannot be blank.");
		}
	}

	private void validIfRolesIsNull(UserCreateDto userCreateDto) {
		boolean isRolesNull = userCreateDto.getRoles() == null;
		if (isRolesNull) {
			throw new IllegalArgumentException("Roles cannot be null.");
		}
	}

}
