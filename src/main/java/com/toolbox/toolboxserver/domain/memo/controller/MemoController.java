package com.toolbox.toolboxserver.domain.memo.controller;

import com.toolbox.toolboxserver.domain.memo.dto.ProjectDTO;
import com.toolbox.toolboxserver.domain.memo.dto.WorkspaceDTO;
import com.toolbox.toolboxserver.domain.memo.entity.Memo;
import com.toolbox.toolboxserver.domain.memo.service.MemoService;
import com.toolbox.toolboxserver.domain.memo.service.ProjectService;
import com.toolbox.toolboxserver.domain.memo.service.WorkspaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/memo")
@Slf4j
public class MemoController {

    private MemoService memoService;
    private WorkspaceService workspaceService;

    private ProjectService projectService;

    public MemoController(MemoService memoService, WorkspaceService workspaceService, ProjectService projectService) {
        this.memoService = memoService;
        this.workspaceService = workspaceService;
        this.projectService = projectService;
    }

    @PostMapping("/users/{userId}/project")
    public ResponseEntity<ProjectDTO> createProject (@PathVariable Long userId, @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(projectDTO));
    }

    @GetMapping("/users/{userId}/project")
    public List<ProjectDTO> getProjectList (@PathVariable Long userId) {
        return projectService.list(userId);
    }

    @PutMapping("/users/{userId}/project/{id}")
    public ResponseEntity<ProjectDTO> modifyProject (@PathVariable Long userId, @PathVariable Long id,@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.modify(projectDTO));
    }

    @DeleteMapping("/users/{userId}/project/{id}")
    public ResponseEntity deleteProject (@PathVariable Long userId, @PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/project/{id}")
    public ProjectDTO getProject (@PathVariable Long id) {
        return projectService.get(id);
    }

    @PostMapping("/users/{userId}/workspace")
    public ResponseEntity<WorkspaceDTO> createWorkspace (@PathVariable Long userId, @RequestBody WorkspaceDTO workspace) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workspaceService.create(workspace));
    }

    @GetMapping("/users/{userId}/workspace")
    public List<WorkspaceDTO> getWorkspaceList (@PathVariable Long userId) {
        return workspaceService.list(userId);
    }

    @GetMapping("/users/{userId}/workspace/{id}")
    public WorkspaceDTO getWorkspace (@PathVariable Long userId, @PathVariable Long id) {
        return workspaceService.get(id);
    }

    @DeleteMapping("/users/{userId}/workspace/{id}")
    public ResponseEntity deleteWorkspace (@PathVariable Long userId, @PathVariable Long id) {
        workspaceService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/users/{userId}/workspace/{id}")
    public ResponseEntity<WorkspaceDTO> modifyWorkspace (@PathVariable Long userId, @PathVariable Long id,@RequestBody WorkspaceDTO workspace) {
        return ResponseEntity.status(HttpStatus.OK).body(workspaceService.modify(workspace));
    }

    @GetMapping("/{id}")
    public Memo getMemo (@PathVariable Long id) {
        return memoService.findById(id);
    }

    @GetMapping("/users/{userId}")
    public List<Memo> getList (@PathVariable Long userId) {
        log.info(":: getList");
        return memoService.list(userId);
    }

    @PostMapping
    public ResponseEntity saveMemo (@RequestBody Memo memo) {
        log.info(":: saveMemo");
        Memo newMemo = memoService.save(memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMemo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMemo (@PathVariable Long id) {
        log.info(":: deleteMemo");
        memoService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
