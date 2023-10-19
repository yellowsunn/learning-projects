package com.yellowsunn.springbootgraphql.presentation.request

import com.yellowsunn.springbootgraphql.application.dto.UpdatePostCommand
import org.springframework.graphql.data.ArgumentValue

data class UpdatePostRequest(
    val id: Long,
    val newTitle: ArgumentValue<String?>,
    val newBody: ArgumentValue<String?>,
) {
    fun convertToCommand(): UpdatePostCommand {
        require(newTitle.nonNull()) { "new title must not be null." }
        require(newBody.nonNull()) { "new body must not be null." }

        return UpdatePostCommand(
            postId = id,
            newTitle = newTitle.value(),
            newBody = newBody.value(),
        )
    }

    private fun <T> ArgumentValue<T>.nonNull(): Boolean {
        return this.isOmitted || this.value() != null
    }
}
