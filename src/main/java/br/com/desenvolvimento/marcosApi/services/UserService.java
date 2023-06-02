package br.com.desenvolvimento.marcosApi.services;

import br.com.desenvolvimento.marcosApi.domain.Users;


public interface UserService {

    Users findById(Integer id);
}
