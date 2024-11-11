package com.hospital.hms.repository;

import com.hospital.hms.model.Lab;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Labrepo extends JpaRepository<Lab,Long> {

}
