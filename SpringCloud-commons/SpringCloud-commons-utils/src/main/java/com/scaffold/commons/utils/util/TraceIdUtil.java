//package com.scaffold.commons.utils.util;
//
//import org.slf4j.MDC;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import java.util.UUID;
//
///**
// * @author dongfeng
// * 2024-07-04 21:57
// */
//public class TraceIdUtil {
//
//    public static final String REGEX = "-";
//
//    public static final String TRACE_ID = "traceId";
//
//    /**
//     * 从header和参数中获取traceId
//     * 从网关传入数据
//     *
//     * @param request 　HttpServletRequest
//     * @return traceId
//     */
//    public static String getTraceIdByRequest(HttpServletRequest request) {
//        String traceId = request.getParameter(TRACE_ID);
//        if (StringUtils.isBlank(traceId)) {
//            traceId = request.getHeader(TRACE_ID);
//        }
//        return traceId;
//    }
//
//    public static String getTraceIdByLocal() {
//        return MDC.get(TRACE_ID);
//    }
//
//    /**
//     * 传递traceId至MDC
//     *
//     * @param traceId 　链路id
//     */
//    public static void setTraceId(String traceId) {
//        if (StringUtil.isNotBlank(traceId)) {
//            MDC.put(TRACE_ID, traceId);
//        }
//    }
//
//    /**
//     * 构建traceId
//     * @return
//     */
//    public static String buildTraceId() {
//        return UUID.randomUUID().toString().replaceAll(REGEX, StringUtils.EMPTY);
//    }
//
//    /**
//     * 清理traceId
//     */
//    public static void cleanTraceId() {
//        MDC.clear();
//    }
//}
//
//
