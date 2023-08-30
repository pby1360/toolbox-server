package com.toolbox.toolboxserver.domain.memo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDTO {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String createdAt;
    private String modifiedAt;
}
