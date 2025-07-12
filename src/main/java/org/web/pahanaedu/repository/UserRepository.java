package org.web.pahanaedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.pahanaedu.model.User;

public interface UserRepository extends JpaRepository<User, String>
{
    User findByUsername(String username);
}
