package com.yellowsunn.springbootgraphql.infrastructure.http.request

data class CreatePostHttpRequest(
    val title: String,
    val body: String,
    val userId: Long,
)
