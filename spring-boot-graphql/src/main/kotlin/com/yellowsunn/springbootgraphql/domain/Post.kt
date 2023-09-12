package com.yellowsunn.springbootgraphql.domain

data class Post(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
)
