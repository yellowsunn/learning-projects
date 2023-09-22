package com.yellowsunn.springbootgraphql.application.dto

data class CreatePostCommand(
    val title: String,
    val body: String,
    val userId: Long,
)
