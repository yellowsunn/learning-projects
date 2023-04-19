package com.yellowsunn.stockconcurrency.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Version

@Entity
class Stock(
    val productId: Long,
    quantity: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var quantity: Long = quantity
        private set

    @Version
    private val version: Long? = null

    fun decrease(quantity: Long) {
        if (this.quantity < quantity) {
            throw IllegalStateException("가진 재고보다 더 많은 양을 요청하였습니다.")
        }
        this.quantity -= quantity
    }
}
