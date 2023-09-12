package com.yellowsunn.springbootgraphql.application.dto

data class CommentDto(
    val id: Long,
    val postId: Long,
    val name: String,
    val email: String,
    val body: String,
)
