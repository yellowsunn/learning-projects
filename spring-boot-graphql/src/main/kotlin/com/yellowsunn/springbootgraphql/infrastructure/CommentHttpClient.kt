package com.yellowsunn.springbootgraphql.infrastructure

import com.yellowsunn.springbootgraphql.domain.Comment
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange

interface CommentHttpClient {
    @GetExchange("/comments")
    fun findComments(
        @RequestParam(required = false) postId: Long? = null,
    ): List<Comment>
}
