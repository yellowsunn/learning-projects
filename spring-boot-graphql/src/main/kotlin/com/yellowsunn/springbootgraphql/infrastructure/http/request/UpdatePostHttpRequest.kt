package com.yellowsunn.springbootgraphql.infrastructure.http.request

data class UpdatePostHttpRequest(
    val title: String,
    val body: String,
)
