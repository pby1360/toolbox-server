package com.toolbox.toolboxserver.domain.memo.repository;

import com.toolbox.toolboxserver.domain.memo.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    List<Workspace> findByUserId(Long userId);
}
