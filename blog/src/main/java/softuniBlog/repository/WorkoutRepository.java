package softuniBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.Workout;

/**
 * Created by admin on 17-Dec-16.
 */
public interface WorkoutRepository extends JpaRepository<Workout,Integer> {


}
