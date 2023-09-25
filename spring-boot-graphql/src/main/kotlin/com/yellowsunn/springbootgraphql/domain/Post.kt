package com.yellowsunn.springbootgraphql.domain

data class Post(
    val id: Long,
    val userId: Long,
    var title: String,
    var body: String,
) {
    fun update(newTitle: String?, newBody: String?) {
        this.title = newTitle ?: this.title
        this.body = newBody ?: this.body
    }
}
