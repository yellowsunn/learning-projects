package com.yellowsunn.springbootgraphql.application

import com.yellowsunn.springbootgraphql.application.converter.GetPostConverter
import com.yellowsunn.springbootgraphql.application.dto.GetPostCommand
import com.yellowsunn.springbootgraphql.application.dto.PostDto
import com.yellowsunn.springbootgraphql.domain.Comment
import com.yellowsunn.springbootgraphql.domain.Post
import com.yellowsunn.springbootgraphql.domain.User
import com.yellowsunn.springbootgraphql.infrastructure.CommentHttpClient
import com.yellowsunn.springbootgraphql.infrastructure.PostHttpClient
import com.yellowsunn.springbootgraphql.infrastructure.UserHttpClient
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postHttpClient: PostHttpClient,
    private val commentHttpClient: CommentHttpClient,
    private val userHttpClient: UserHttpClient,
) {
    fun getPostById(command: GetPostCommand): PostDto {
        val post: Post = requireNotNull(
            postHttpClient.findByPostId(command.postId),
        ) { "게시글을 찾을 수 없습니다." }

        val comments: List<Comment> = findComments(
            isSelected = command.isCommentsSelected,
            postId = command.postId,
        )

        val user: User? = findUser(
            isSelected = command.isUserSelected,
            userId = post.userId,
        )

        return GetPostConverter.INSTANCE.convertToDto(
            post = post,
            comments = comments,
            user = user,
        )
    }

    private fun findComments(isSelected: Boolean, postId: Long): List<Comment> {
        return if (isSelected) {
            commentHttpClient.findComments(postId = postId)
        } else {
            emptyList()
        }
    }

    private fun findUser(isSelected: Boolean, userId: Long): User? {
        return if (isSelected) {
            userHttpClient.findUserById(userId = userId)
        } else {
            null
        }
    }
}
