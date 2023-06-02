package br.com.desenvolvimento.marcosApi.repository;

import br.com.desenvolvimento.marcosApi.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {


}
