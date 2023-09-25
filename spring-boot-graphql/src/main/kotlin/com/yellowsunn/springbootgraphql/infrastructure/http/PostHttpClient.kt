package com.yellowsunn.springbootgraphql.infrastructure.http

import com.yellowsunn.springbootgraphql.domain.Post
import com.yellowsunn.springbootgraphql.infrastructure.http.request.CreatePostHttpRequest
import com.yellowsunn.springbootgraphql.infrastructure.http.request.UpdatePostHttpRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.PatchExchange
import org.springframework.web.service.annotation.PostExchange

interface PostHttpClient {
    @GetExchange("/posts/{postId}")
    fun findByPostId(@PathVariable postId: Long): Post?

    @PostExchange("/posts")
    fun createPost(@RequestBody request: CreatePostHttpRequest): Post

    @PatchExchange("/posts/{postId}")
    fun updateBoyPostId(
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostHttpRequest,
    ): Post
}
