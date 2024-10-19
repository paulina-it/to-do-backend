package uk.bovykina.to_do.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.bovykina.to_do.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskCreateDto {
    @Size(min = 3)
    @NotBlank(message = "Please enter the task")
    private String text;
    private Long userId;
}
