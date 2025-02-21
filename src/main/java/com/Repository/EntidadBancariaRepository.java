package com.Repository;

import com.Model.EntidadBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface EntidadBancariaRepository extends JpaRepository<EntidadBancaria, Long> {
}
