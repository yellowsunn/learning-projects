package com.yellowsunn.springbootgraphql.controller

import com.yellowsunn.springbootgraphql.application.PostService
import com.yellowsunn.springbootgraphql.application.dto.PostDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController(
    private val postService: PostService,
) {
    @GetMapping("/posts/{postId}")
    fun findComments(@PathVariable postId: Long): PostDto {
        return postService.getPostById(postId)
    }
}
