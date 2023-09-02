package com.toolbox.toolboxserver.domain.memo.entity;

import com.toolbox.toolboxserver.domain.memo.dto.ProjectDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private String description;
    @OneToMany(mappedBy = "project")
    private List<Workspace> workspaceList = new ArrayList<>();
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;



    private Project(Long userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public static Project create(ProjectDTO dto) {
        return new Project(dto.getUserId(), dto.getName(), dto.getDescription());
    }

    public void modify (String name, String description) {
        this.name = name;
        this.description = description;
    }
}
