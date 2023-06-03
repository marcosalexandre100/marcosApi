package br.com.desenvolvimento.marcosApi.resources;

import br.com.desenvolvimento.marcosApi.config.ModelMapperConfig;
import br.com.desenvolvimento.marcosApi.domain.Users;
import br.com.desenvolvimento.marcosApi.domain.dto.UsersDTO;
import br.com.desenvolvimento.marcosApi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")

public class UserResource {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> finById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UsersDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll()
                .stream().map(x -> mapper.map(x, UsersDTO.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UsersDTO> creat(@RequestBody UsersDTO obj){
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}").buildAndExpand(service.create(obj).getId()).toUri();
        return ResponseEntity.created(uri).build();

    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> update(@RequestBody UsersDTO obj, @PathVariable Integer id){
        obj.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(obj), UsersDTO.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
