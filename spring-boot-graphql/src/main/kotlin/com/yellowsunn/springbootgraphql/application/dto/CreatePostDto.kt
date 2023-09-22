package com.yellowsunn.springbootgraphql.application.dto

data class CreatePostDto(
    val id: Long,
    val title: String,
    val body: String,
    val userId: Long,
)
