package br.com.desenvolvimento.marcosApi.services;

import br.com.desenvolvimento.marcosApi.domain.Users;
import br.com.desenvolvimento.marcosApi.domain.dto.UsersDTO;

import java.util.List;


public interface UserService {

    Users findById(Integer id);
    List<Users> findAll();
    Users create(UsersDTO obj);
    Users update(UsersDTO obj);
    void delete(Integer id);
}
