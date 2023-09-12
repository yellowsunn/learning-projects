package com.yellowsunn.springbootgraphql.application

import com.yellowsunn.springbootgraphql.application.converter.PostConverter
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
    fun getPostById(postId: Long): PostDto {
        val post: Post = requireNotNull(
            postHttpClient.findByPostId(postId),
        ) { "게시글을 찾을 수 없습니다." }

        val comments: List<Comment> = commentHttpClient.findComments(postId = post.id)

        val user: User? = userHttpClient.findUserById(userId = post.userId)

        return PostConverter.INSTANCE.convertToDto(
            post = post,
            comments = comments,
            user = user,
        )
    }
}
