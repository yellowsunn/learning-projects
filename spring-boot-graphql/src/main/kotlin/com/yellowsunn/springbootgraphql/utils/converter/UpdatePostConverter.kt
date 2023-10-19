package com.yellowsunn.springbootgraphql.utils.converter

import com.yellowsunn.springbootgraphql.application.dto.UpdatePostDto
import com.yellowsunn.springbootgraphql.domain.Post
import com.yellowsunn.springbootgraphql.infrastructure.http.request.UpdatePostHttpRequest
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface UpdatePostConverter {
    companion object {
        val INSTANCE: UpdatePostConverter = Mappers.getMapper(UpdatePostConverter::class.java)
    }

    fun convertDomainToHttpRequest(post: Post): UpdatePostHttpRequest

    fun convertDomainToDto(post: Post): UpdatePostDto
}
