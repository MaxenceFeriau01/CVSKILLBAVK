package com.ensemble.entreprendre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensemble.entreprendre.domain.FileDb;

public interface IFileDbRepository extends JpaRepository<FileDb, Long> {
}
