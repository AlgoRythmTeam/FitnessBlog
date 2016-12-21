package softuniBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.Workout;



public interface WorkoutRepository extends JpaRepository<Workout,Integer> {

}
