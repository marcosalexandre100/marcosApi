package br.com.desenvolvimento.marcosApi.services.impl;

import br.com.desenvolvimento.marcosApi.domain.Users;
import br.com.desenvolvimento.marcosApi.domain.dto.UsersDTO;
import br.com.desenvolvimento.marcosApi.repository.UserRepository;
import br.com.desenvolvimento.marcosApi.services.expection.DataIntegratyViolationException;
import br.com.desenvolvimento.marcosApi.services.expection.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;
    private UsersDTO usersDTO;
    private Users users;
    private Optional<Users> optionalUsers;

    private static final Integer ID = 1;
    private static final String NAME = "Valdir";
    private static final String EMAIL = "valdir@mail.com";
    private static final String PASSWORD = "123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        starUser();
    }

    private void starUser() {
        users = new Users(ID, NAME, EMAIL, PASSWORD);
        usersDTO = new UsersDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUsers = Optional.of(new Users(ID, NAME, EMAIL, PASSWORD));
    }

    @Test
    @DisplayName("Quando encontrar pelo Id então retornar um instância de usuários")
    void cenario01() {
        when(repository.findById(Mockito.anyInt())).thenReturn(optionalUsers);

        Users response = service.findById(1);

        Assertions.assertNotNull(response);

        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    @DisplayName("Deve retornar Objeto não encontrado e lancar exception")
    void cenario02() {
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try {
            service.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }
    }

    @Test
    @DisplayName("Deve retorna uma Lista de Users")
    void cenario03() {
        when(repository.findAll()).thenReturn(List.of(users));

        List<Users> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Users.class, response.get(0).getClass());

        assertEquals(ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(EMAIL, response.get(0).getEmail());
        assertEquals(PASSWORD, response.get(0).getPassword());
    }
    @Test
    @DisplayName("Quando criar um usuário deve retornar a sucesso")
    void cenario04(){
        when(repository.save(any())).thenReturn(users);

        Users response = service.create(usersDTO);

        assertNotNull(response);
        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }
    @Test
    @DisplayName("Deve retornar um DataIntegrituViolationException")
    void cenario05(){
        when(repository.findByEmail(anyString())).thenReturn(optionalUsers);

        try{
            optionalUsers.get().setId(2);
        }catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals("E-mail já cadastrado no sistema", ex.getMessage());
        }
    }
    @Test
    @DisplayName("Deve retorno sucesso ao deletar um usuario")
    void cenario06(){
        when(repository.findById(anyInt())).thenReturn(optionalUsers);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
    }
    @Test
    @DisplayName("Deve lançar um ObjectNotFounfException")
    void cenario07(){
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
        try{
            service.delete(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }
    }
}