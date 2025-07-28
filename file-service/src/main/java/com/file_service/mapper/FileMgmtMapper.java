package com.file_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.file_service.dto.FileInfo;
import com.file_service.entity.FileMgmt;

@Mapper(componentModel = "spring")
public interface FileMgmtMapper {
    @Mapping(target = "id", source = "name")
    FileMgmt toFileMgmt(FileInfo fileInfo);
}
