package com.examples.SunatRetrofit.SunatRetrofit.repository;

import com.examples.SunatOpenfeign.SunatOpenfeign.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity,Long> {
}
