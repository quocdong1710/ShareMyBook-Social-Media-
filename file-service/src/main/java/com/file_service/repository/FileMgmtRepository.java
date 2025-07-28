package com.file_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.file_service.entity.FileMgmt;

@Repository
public interface FileMgmtRepository extends MongoRepository<FileMgmt, String> {

}
