package com.yellowsunn.springbootgraphql.presentation

import com.yellowsunn.springbootgraphql.application.PostService
import com.yellowsunn.springbootgraphql.application.dto.CreatePostDto
import com.yellowsunn.springbootgraphql.application.dto.GetPostCommand
import com.yellowsunn.springbootgraphql.application.dto.GetPostDto
import com.yellowsunn.springbootgraphql.application.dto.UpdatePostDto
import com.yellowsunn.springbootgraphql.presentation.request.CreatePostRequest
import com.yellowsunn.springbootgraphql.presentation.request.UpdatePostRequest
import com.yellowsunn.springbootgraphql.utils.converter.CreatePostConverter
import graphql.schema.DataFetchingFieldSelectionSet
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
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
    ): GetPostDto {
        logger.info("fetch post. id={}", postId)

        val command = GetPostCommand(
            postId = postId,
            isCommentsSelected = selections.contains("comments"),
            isUserSelected = selections.contains("user"),
        )
        return postService.getPostById(command)
    }

    @MutationMapping
    fun createPost(
        @Argument input: CreatePostRequest,
    ): CreatePostDto {
        return postService.creatPost(
            command = CreatePostConverter.INSTANCE.convertRequestToCommand(input),
        )
    }

    @MutationMapping
    fun updatePost(
        @Argument input: UpdatePostRequest,
    ): UpdatePostDto {
        return postService.updatePost(
            input.convertToCommand(),
        )
    }
}
