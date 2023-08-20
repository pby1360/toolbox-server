package com.toolbox.toolboxserver.domain.memo.entity;

import com.toolbox.toolboxserver.domain.memo.dto.WorkspaceDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private String description;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private Workspace(Long userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public static Workspace create(WorkspaceDTO dto) {
        return new Workspace(dto.getUserId(), dto.getName(), dto.getDescription());
    }

    public void modify (String name, String description) {
        this.name = name;
        this.description = description;
    }

}
