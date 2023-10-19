package com.yellowsunn.springbootgraphql.application.dto

data class UpdatePostCommand(
    val postId: Long,
    val newTitle: String?,
    val newBody: String?,
)
