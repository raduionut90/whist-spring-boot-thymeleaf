package com.ionutradu.whistspringbootthymeleaf.authentication.service;


import com.ionutradu.whistspringbootthymeleaf.authentication.model.CrmUser;
import com.ionutradu.whistspringbootthymeleaf.authentication.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	public void save(CrmUser crmUser);
}
