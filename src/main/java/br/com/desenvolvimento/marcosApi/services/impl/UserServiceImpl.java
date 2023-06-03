package br.com.desenvolvimento.marcosApi.services.impl;

import br.com.desenvolvimento.marcosApi.domain.Users;
import br.com.desenvolvimento.marcosApi.domain.dto.UsersDTO;
import br.com.desenvolvimento.marcosApi.repository.UserRepository;
import br.com.desenvolvimento.marcosApi.services.UserService;
import br.com.desenvolvimento.marcosApi.services.expection.DataIntegratyViolationException;
import br.com.desenvolvimento.marcosApi.services.expection.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public Users findById(Integer id) {
       Optional<Users> obj = repository.findById(id);
       return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public List<Users> findAll() {
       return repository.findAll();
    }

    @Override
    public Users create(UsersDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj,Users.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    @Override
    public Users update(UsersDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj,Users.class));
    }




    private void findByEmail(UsersDTO obj) {
       Optional<Users> users = repository.findByEmail(obj.getEmail());
       if(users.isPresent() && !users.get().getId().equals(obj.getId())){
           throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
       }
    }
}
