package com.scaffold.gateway.fegin;

import com.scaffold.commons.utils.vo.Result;
import reactor.core.publisher.Mono;

/**
 * @author dongfeng
 * 2024-07-04 22:38
 */
public interface AuthService {
    Mono<Result> authenticate(String token);
}
