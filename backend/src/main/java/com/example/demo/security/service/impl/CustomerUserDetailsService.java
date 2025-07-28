package com.example.demo.security.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service // Mapper를 기반으로 데이터베이스 인증 방식을 위한 Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Mapper를 활용해서 DB에 접근
		UserVO userVO = userMapper.getUserInfo(username);

		if (userVO == null) {
			throw new UsernameNotFoundException(username);
		}
		return new LoginUserVO(userVO);
	}

}
