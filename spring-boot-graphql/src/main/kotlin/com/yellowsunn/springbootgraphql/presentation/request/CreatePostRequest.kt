package com.yellowsunn.springbootgraphql.presentation.request

data class CreatePostRequest(
    val title: String,
    val body: String,
    val userId: Long,
)
