package com.yellowsunn.stockconcurrency.facade

import com.yellowsunn.stockconcurrency.repository.RedisLockRepository
import com.yellowsunn.stockconcurrency.service.StockService
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class LettuceLockStockFacade(
    private val stockService: StockService,
    private val redisLockRepository: RedisLockRepository,
) {
    fun decrease(id: Long, quantity: Long) {
        while (redisLockRepository.lock(id).not()) {
            TimeUnit.MILLISECONDS.sleep(100L)
        }

        try {
            stockService.decrease(id, quantity)
        } finally {
            redisLockRepository.unlock(id)
        }
    }
}
