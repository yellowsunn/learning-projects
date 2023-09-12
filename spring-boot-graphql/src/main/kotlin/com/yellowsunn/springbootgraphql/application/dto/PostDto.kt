package com.yellowsunn.springbootgraphql.application.dto

data class PostDto(
    val id: Long,
    val title: String,
    val body: String,
    val comments: List<CommentDto>,
    val user: UserDto?,
)
