package softuniBlog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.Rating;

public interface RatingRepository  extends JpaRepository<Rating,Integer> {
}
