package ru.mariknv86.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ResultsResponseDto<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T errors;

}
