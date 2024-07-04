package com.scaffold.gateway.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.UUID;

/**
 * @author dongfeng
 * 2024-07-04 21:57
 */
public class TraceIdUtil {

    public static final String REGEX = "-";

    public static final String TRACE_ID = "trace_Id";

    /**
     * 从header和参数中获取traceId
     * 从网关传入数据
     *
     * @param request 　HttpServletRequest
     * @return traceId
     */
    public static String getTraceIdByRequest(ServerHttpRequest request) {
        String traceId = request.getQueryParams().getFirst(TRACE_ID);

        if (StringUtils.isBlank(traceId)) {
            HttpHeaders headers = request.getHeaders();
            traceId = headers.getFirst(TRACE_ID);
        }
        return traceId;
    }

    public static String getTraceIdByLocal() {
        return MDC.get(TRACE_ID);
    }

    /**
     * 传递traceId至MDC
     *
     * @param traceId 　链路id
     */
    public static void setTraceId(String traceId) {
        if (StringUtils.isNotBlank(traceId)) {
            MDC.put(TRACE_ID, traceId);
        }
    }

    /**
     * 构建traceId
     */
    public static String buildTraceId() {
        return UUID.randomUUID().toString().replaceAll(REGEX, "");
    }

    /**
     * 清理traceId
     */
    public static void cleanTraceId() {
        MDC.clear();
    }
}


