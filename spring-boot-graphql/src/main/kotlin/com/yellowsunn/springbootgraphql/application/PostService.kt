package com.yellowsunn.springbootgraphql.application

import com.yellowsunn.springbootgraphql.application.dto.*
import com.yellowsunn.springbootgraphql.domain.Comment
import com.yellowsunn.springbootgraphql.domain.Post
import com.yellowsunn.springbootgraphql.domain.User
import com.yellowsunn.springbootgraphql.infrastructure.http.CommentHttpClient
import com.yellowsunn.springbootgraphql.infrastructure.http.PostHttpClient
import com.yellowsunn.springbootgraphql.infrastructure.http.UserHttpClient
import com.yellowsunn.springbootgraphql.infrastructure.http.request.UpdatePostHttpRequest
import com.yellowsunn.springbootgraphql.utils.converter.CreatePostConverter
import com.yellowsunn.springbootgraphql.utils.converter.GetPostConverter
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postHttpClient: PostHttpClient,
    private val commentHttpClient: CommentHttpClient,
    private val userHttpClient: UserHttpClient,
) {
    fun getPostById(command: GetPostCommand): GetPostDto {
        val post: Post = requireNotNull(
            postHttpClient.findByPostId(command.postId),
        ) { "게시글을 찾을 수 없습니다." }

        val comments: List<Comment> = findComments(
            postId = command.postId,
            isSelected = command.isCommentsSelected,
        )

        val user: User? = findUser(
            userId = post.userId,
            isSelected = command.isUserSelected,
        )

        return GetPostConverter.INSTANCE.convertDomainsToDto(
            post = post,
            comments = comments,
            user = user,
        )
    }

    fun creatPost(command: CreatePostCommand): CreatePostDto {
        checkNotNull(
            findUser(command.userId),
        ) { "사용자를 찾을 수 없습니다." }

        val post: Post = postHttpClient.createPost(
            request = CreatePostConverter.INSTANCE.convertCommandToHttpRequest(command),
        )

        return CreatePostConverter.INSTANCE.convertDomainToDto(post)
    }

    fun updatePost(command: UpdatePostCommand): UpdatePostDto {
        val post: Post = postHttpClient.findByPostId(command.postId)
            ?: throw IllegalArgumentException("게시글을 찾을 수 없습니다.")

        post.update(
            newTitle = command.newTitle,
            newBody = command.newBody,
        )

        val updatedPost = postHttpClient.updateBoyPostId(
            postId = post.id,
            request = UpdatePostHttpRequest(
                title = post.title,
                body = post.body,
            ),
        )
        return UpdatePostDto(
            id = updatedPost.id,
            title = updatedPost.title,
            body = updatedPost.body,
            userId = updatedPost.userId,
        )
    }

    private fun findComments(postId: Long, isSelected: Boolean = true): List<Comment> {
        return if (isSelected) {
            commentHttpClient.findComments(postId = postId)
        } else {
            emptyList()
        }
    }

    private fun findUser(userId: Long, isSelected: Boolean = true): User? {
        return if (isSelected) {
            userHttpClient.findUserById(userId = userId)
        } else {
            null
        }
    }
}
