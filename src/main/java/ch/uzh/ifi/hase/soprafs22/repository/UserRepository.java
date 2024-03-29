package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.user.RegisteredUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("registeredUserRepository")
public interface UserRepository extends JpaRepository<RegisteredUser, Long> {
    RegisteredUser findRegisteredUserByToken(String token);
    RegisteredUser findRegisteredUserByUsername(String username);

    List<RegisteredUser> findAllByOrderByRankedScoreAsc(Pageable pageable);
    List<RegisteredUser> findAllByOrderByRankedScoreDesc(Pageable pageable);

    List<RegisteredUser> findAllByOrderByWinsAsc(Pageable pageable);
    List<RegisteredUser> findAllByOrderByWinsDesc(Pageable pageable);

    List<RegisteredUser> findAllByOrderByLossesAsc(Pageable pageable);
    List<RegisteredUser> findAllByOrderByLossesDesc(Pageable pageable);

}
