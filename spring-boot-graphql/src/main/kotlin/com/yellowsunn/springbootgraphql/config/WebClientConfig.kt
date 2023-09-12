package com.yellowsunn.springbootgraphql.config

import com.yellowsunn.springbootgraphql.infrastructure.CommentHttpClient
import com.yellowsunn.springbootgraphql.infrastructure.PostHttpClient
import com.yellowsunn.springbootgraphql.infrastructure.UserHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

@Configuration
class WebClientConfig {
    @Bean
    fun jsonPlaceHolderClient(): WebClient {
        return WebClient.builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
    }

    @Bean
    fun commentHttpClient(
        jsonPlaceHolderClient: WebClient,
    ): CommentHttpClient {
        return HttpServiceProxyFactory.builder(
            WebClientAdapter.forClient(jsonPlaceHolderClient),
        )
            .build()
            .createClient()
    }

    @Bean
    fun postHttpClient(
        jsonPlaceHolderClient: WebClient,
    ): PostHttpClient {
        return HttpServiceProxyFactory.builder(
            WebClientAdapter.forClient(jsonPlaceHolderClient),
        )
            .build()
            .createClient()
    }

    @Bean
    fun userHttpClient(
        jsonPlaceHolderClient: WebClient,
    ): UserHttpClient {
        return HttpServiceProxyFactory.builder(
            WebClientAdapter.forClient(jsonPlaceHolderClient),
        )
            .build()
            .createClient()
    }
}
