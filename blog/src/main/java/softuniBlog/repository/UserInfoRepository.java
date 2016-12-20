package softuniBlog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
}
