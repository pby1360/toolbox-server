package com.toolbox.toolboxserver.domain.memo.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class WorkspaceDTO {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String createdAt;
    private String modifiedAt;
}
