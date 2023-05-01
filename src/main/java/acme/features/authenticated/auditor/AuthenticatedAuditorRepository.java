
package acme.features.authenticated.auditor;

import org.springframework.data.jpa.repository.Query;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

public interface AuthenticatedAuditorRepository extends AbstractRepository {

	@Query("SELECT ua FROM UserAccount ua WHERE ua.id = :id")
	UserAccount findUserAccountById(int id);

	@Query("SELECT a FROM Auditor a WHERE a.userAccount.id = :id")
	Auditor findAuditorByUserAccountId(int id);

}
