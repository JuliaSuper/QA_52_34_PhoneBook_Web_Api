package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ErrorMessageDto {
        private String timestamp;
        private int status;
        private String error;
        private Object message;
        private String path;
    }

