package mx.edu.utez.sda.jwtspring8c.repository;

import mx.edu.utez.sda.jwtspring8c.model.UserInfo;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    public Optional<UserInfo> findByUsername(String username);
}
