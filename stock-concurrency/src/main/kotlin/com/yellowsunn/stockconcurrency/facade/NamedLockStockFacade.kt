package com.yellowsunn.stockconcurrency.facade

import com.yellowsunn.stockconcurrency.repository.LockRepository
import com.yellowsunn.stockconcurrency.service.StockService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class NamedLockStockFacade(
    private val stockService: StockService,
    private val lockRepository: LockRepository,
) {
    @Transactional
    fun decrease(id: Long, quantity: Long) {
        try {
            lockRepository.getLock(id.toString())
            stockService.decrease(id, quantity)
        } finally {
            lockRepository.releaseLock(id.toString())
        }
    }
}
