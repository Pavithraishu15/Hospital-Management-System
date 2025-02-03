package com.hospital.hms.repository;

import com.hospital.hms.model.Employee;
import com.hospital.hms.model.Lab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface Labrepo extends JpaRepository<Lab,Long> {
    Optional<Lab> findByUseRname(String useRname);

}
