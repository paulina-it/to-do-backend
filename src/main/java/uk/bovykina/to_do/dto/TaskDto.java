package uk.bovykina.to_do.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.bovykina.to_do.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskDto {
    private Long id;
    private String text;
    private boolean isDone;
    private Long userId;
}
