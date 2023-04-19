package com.yellowsunn.stockconcurrency.repository

import com.yellowsunn.stockconcurrency.domain.Stock
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface StockRepository : JpaRepository<Stock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findPessimisticLockStockById(id: Long): Stock?
}
