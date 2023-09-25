package com.yellowsunn.springbootgraphql.application.dto

data class UpdatePostDto(
    val id: Long,
    val title: String,
    val body: String,
    val userId: Long,
)
