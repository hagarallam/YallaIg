package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<File,Integer> {
}
