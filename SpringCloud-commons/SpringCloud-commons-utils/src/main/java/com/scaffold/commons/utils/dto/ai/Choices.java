package com.scaffold.commons.utils.dto.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Choices {
    @JsonProperty("finish_reason")
    private String finishReason;

    @JsonProperty("index")
    private int index;

    @JsonProperty("message")
    private Message message;

}
