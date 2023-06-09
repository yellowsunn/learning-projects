package com.yellowsunn.stockconcurrency.service

import com.yellowsunn.stockconcurrency.domain.Stock
import com.yellowsunn.stockconcurrency.repository.StockRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PessimisticLockStockService(
    private val stockRepository: StockRepository,
) {

    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val stock: Stock = stockRepository.findPessimisticLockStockById(id)
            ?: throw IllegalArgumentException("재고 정보를 찾을 수 없습니다.")

        stock.decrease(quantity)
    }
}
