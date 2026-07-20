package org.example.megahottakes.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HotTakeDTO {
    private Long id;
    private String content;
    private String tag;
    private Integer heatCount;
    private Integer coldCount;
    private String authorName;
    private Long authorId;
    private LocalDateTime creationDate;
    private String pulledByUsername;
    private LocalDateTime lastResurfacedDate;
}
