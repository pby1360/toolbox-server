package com.toolbox.toolboxserver.domain.memo.service.impl;

import com.toolbox.toolboxserver.domain.memo.dto.WorkspaceDTO;
import com.toolbox.toolboxserver.domain.memo.entity.Workspace;
import com.toolbox.toolboxserver.domain.memo.repository.WorkspaceRepository;
import com.toolbox.toolboxserver.domain.memo.service.WorkspaceService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    WorkspaceRepository repository;

    public WorkspaceServiceImpl(WorkspaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public WorkspaceDTO get(Long id) {
        Workspace workspace = repository.findById(id).get();
        return WorkspaceDTO.builder()
                .id(workspace.getId())
                .userId(workspace.getUserId())
                .name(workspace.getName())
                .description(workspace.getDescription())
                .createdAt(workspace.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifiedAt(workspace.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @Override
    public WorkspaceDTO create(WorkspaceDTO workspaceDto) {
        Workspace workspace = Workspace.create(workspaceDto);
        repository.save(workspace);
        WorkspaceDTO newWorkspace = WorkspaceDTO
                .builder()
                .id(workspace.getId())
                .userId(workspace.getUserId())
                .name(workspace.getName())
                .description(workspace.getDescription())
                .createdAt(workspace.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifiedAt(workspace.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        return newWorkspace;
    }

    @Override
    public WorkspaceDTO modify(WorkspaceDTO workspaceDto) {
        Workspace workspace = repository.findById(workspaceDto.getId()).get();
        workspace.modify(workspaceDto.getName(), workspaceDto.getDescription());

        repository.save(workspace);

        WorkspaceDTO modifiedWorkspace = WorkspaceDTO
                .builder()
                .id(workspace.getId())
                .userId(workspace.getUserId())
                .name(workspace.getName())
                .description(workspace.getDescription())
                .createdAt(workspace.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifiedAt(workspace.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        return modifiedWorkspace;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<WorkspaceDTO> list(Long userId) {
        List<Workspace> list = repository.findByUserId(userId);
        return list.stream().map(workspace ->
                WorkspaceDTO
                        .builder()
                        .id(workspace.getId())
                        .userId(workspace.getUserId())
                        .name(workspace.getName())
                        .description(workspace.getDescription())
                        .createdAt(workspace.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .modifiedAt(workspace.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build()).collect(Collectors.toList());
    }
}
