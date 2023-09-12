package com.yellowsunn.springbootgraphql.infrastructure

import com.yellowsunn.springbootgraphql.domain.Post
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange

interface PostHttpClient {
    @GetExchange("/posts/{postId}")
    fun findByPostId(@PathVariable postId: Long): Post?
}
