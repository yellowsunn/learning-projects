package com.yellowsunn.springbootgraphql.presentation

import com.yellowsunn.springbootgraphql.application.PostService
import com.yellowsunn.springbootgraphql.application.dto.GetPostCommand
import com.yellowsunn.springbootgraphql.application.dto.PostDto
import graphql.schema.DataFetchingFieldSelectionSet
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class PostController(
    private val postService: PostService,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @QueryMapping
    fun findPost(
        @Argument("id") postId: Long,
        selections: DataFetchingFieldSelectionSet,
    ): PostDto {
        logger.info("fetch post. id={}", postId)

        val command = GetPostCommand(
            postId = postId,
            isCommentsSelected = selections.contains("comments"),
            isUserSelected = selections.contains("user"),
        )
        return postService.getPostById(command)
    }
}
