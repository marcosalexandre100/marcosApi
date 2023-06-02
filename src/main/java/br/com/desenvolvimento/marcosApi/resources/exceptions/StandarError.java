package br.com.desenvolvimento.marcosApi.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandarError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
}
