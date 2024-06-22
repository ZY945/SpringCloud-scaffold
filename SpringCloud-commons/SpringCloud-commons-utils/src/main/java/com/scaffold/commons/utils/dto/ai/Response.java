package com.scaffold.commons.utils.dto.ai;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scaffold.commons.utils.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    @JsonProperty("code")
    private int code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private Data data;

    public String getResponseContent() {
        return data.getChoices()[0].getMessage().getContent();
    }

    public String getModel() {
        return data.getModel();
    }

    public String getCreatedTime() {
        return TimeUtil.getCurrentTime(data.getCreated());
    }
}
