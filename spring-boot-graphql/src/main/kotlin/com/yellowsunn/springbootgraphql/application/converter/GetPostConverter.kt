package com.yellowsunn.springbootgraphql.application.converter

import com.yellowsunn.springbootgraphql.application.dto.PostDto
import com.yellowsunn.springbootgraphql.domain.Comment
import com.yellowsunn.springbootgraphql.domain.Post
import com.yellowsunn.springbootgraphql.domain.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface GetPostConverter {
    companion object {
        val INSTANCE: GetPostConverter = Mappers.getMapper(GetPostConverter::class.java)
    }

    @Mapping(source = "post.id", target = "id")
    @Mapping(source = "post.title", target = "title")
    @Mapping(source = "post.body", target = "body")
    fun convertToDto(
        post: Post,
        comments: List<Comment>,
        user: User?,
    ): PostDto
}
