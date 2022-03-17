package it.epicode.be.energy.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.energy.security.model.Role;
import it.epicode.be.energy.security.model.Roles;




public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(Roles role);
}
