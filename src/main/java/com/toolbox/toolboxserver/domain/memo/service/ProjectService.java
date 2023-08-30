package com.toolbox.toolboxserver.domain.memo.service;

import com.toolbox.toolboxserver.domain.memo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    ProjectDTO get (Long id);
    ProjectDTO create (ProjectDTO project);
    ProjectDTO modify (ProjectDTO project);
    void delete(Long id);
    List<ProjectDTO> list (Long userId);
}
