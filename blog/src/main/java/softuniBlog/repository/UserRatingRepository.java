package softuniBlog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.UserRating;

public interface UserRatingRepository extends JpaRepository<UserRating,Integer> {
}
