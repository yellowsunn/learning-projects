package com.yellowsunn.springbootgraphql.application.dto

data class GetPostCommand(
    val postId: Long,
    val isCommentsSelected: Boolean,
    val isUserSelected: Boolean,
)
