package com.toolbox.toolboxserver.domain.memo.service.impl;

import com.toolbox.toolboxserver.domain.memo.dto.ProjectDTO;
import com.toolbox.toolboxserver.domain.memo.entity.Project;
import com.toolbox.toolboxserver.domain.memo.repository.ProjectRepository;
import com.toolbox.toolboxserver.domain.memo.service.ProjectService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository repository;

    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProjectDTO get(Long id) {
        Project project = repository.findById(id).get();
        return ProjectDTO.builder()
                .id(project.getId())
                .userId(project.getUserId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifiedAt(project.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @Override
    public ProjectDTO create(ProjectDTO projectDto) {
        Project project = Project.create(projectDto);
        repository.save(project);
        ProjectDTO newProject = ProjectDTO
                .builder()
                .id(project.getId())
                .userId(project.getUserId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifiedAt(project.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        return newProject;
    }

    @Override
    public ProjectDTO modify(ProjectDTO projectDto) {
        Project project = repository.findById(projectDto.getId()).get();
        project.modify(projectDto.getName(), projectDto.getDescription());

        repository.save(project);

        ProjectDTO modifiedProject = ProjectDTO
                .builder()
                .id(project.getId())
                .userId(project.getUserId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifiedAt(project.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        return modifiedProject;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProjectDTO> list(Long userId) {
        List<Project> list = repository.findByUserId(userId);
        return list.stream().map(project ->
                ProjectDTO
                        .builder()
                        .id(project.getId())
                        .userId(project.getUserId())
                        .name(project.getName())
                        .description(project.getDescription())
                        .createdAt(project.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .modifiedAt(project.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build()).collect(Collectors.toList());
    }
}
