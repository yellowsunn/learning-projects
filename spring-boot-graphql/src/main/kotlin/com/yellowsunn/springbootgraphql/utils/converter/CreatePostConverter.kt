package com.yellowsunn.springbootgraphql.utils.converter

import com.yellowsunn.springbootgraphql.application.dto.CreatePostCommand
import com.yellowsunn.springbootgraphql.application.dto.CreatePostDto
import com.yellowsunn.springbootgraphql.domain.Post
import com.yellowsunn.springbootgraphql.infrastructure.http.request.CreatePostHttpRequest
import com.yellowsunn.springbootgraphql.presentation.request.CreatePostRequest
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface CreatePostConverter {
    companion object {
        val INSTANCE: CreatePostConverter = Mappers.getMapper(CreatePostConverter::class.java)
    }

    fun convertRequestToCommand(request: CreatePostRequest): CreatePostCommand

    fun convertCommandToHttpRequest(command: CreatePostCommand): CreatePostHttpRequest

    fun convertDomainToDto(post: Post): CreatePostDto
}
