package com.yellowsunn.springbootgraphql.infrastructure

import com.yellowsunn.springbootgraphql.domain.User
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange

interface UserHttpClient {
    @GetExchange("/users/{userId}")
    fun findUserById(@PathVariable userId: Long): User?
}
