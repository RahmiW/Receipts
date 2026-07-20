package org.example.megahottakes.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationDTO {
    private Long id;
    private Long hotTakeId;
    private String hotTakeContent;
    private String message;
    private LocalDateTime createdDate;
    private boolean read;
}
