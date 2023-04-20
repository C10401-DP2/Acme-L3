/*
 * AnyDutyRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.feature.any.peep;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.peep.Peep;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("select p from Peep p where p.id = :masterId")
	Peep findOnePeepById(int masterId);

	@Query("select p from Peep p")
	Collection<Peep> findAllPeeps();

	@Query("select u from UserAccount u")
	Collection<UserAccount> findAllUsers();

	@Query("SELECT u FROM UserAccount u WHERE u.username = :username")
	UserAccount findUserByUsername(String username);
}
