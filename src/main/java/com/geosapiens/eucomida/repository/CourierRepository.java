package com.geosapiens.eucomida.repository;

import com.geosapiens.eucomida.entity.Courier;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, UUID> {

}
