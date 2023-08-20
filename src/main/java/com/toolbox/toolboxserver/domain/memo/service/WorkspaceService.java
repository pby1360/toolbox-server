package com.toolbox.toolboxserver.domain.memo.service;

import com.toolbox.toolboxserver.domain.memo.dto.WorkspaceDTO;

import java.util.List;

public interface WorkspaceService {

    WorkspaceDTO get (Long id);
    WorkspaceDTO create (WorkspaceDTO workspace);
    WorkspaceDTO modify (WorkspaceDTO workspace);
    void delete(Long id);
    List<WorkspaceDTO> list (Long userId);
}
