package com.example.bds.repository;

import com.example.bds.entity.BlacklistToken;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken,Integer> {

    BlacklistToken findByToken(String token);
}
