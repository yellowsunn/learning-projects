package com.yellowsunn.stockconcurrency.facade

import com.yellowsunn.stockconcurrency.service.StockService
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedissonLockStockFacade(
    private val stockService: StockService,
    private val redissonClient: RedissonClient,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    fun decrease(id: Long, quantity: Long) {
        val lock: RLock = redissonClient.getLock(id.toString())

        try {
            val available: Boolean = lock.tryLock(5L, 1L, TimeUnit.SECONDS)
            if (available.not()) {
                log.error("lock 획득 실패")
                return
            }
            stockService.decrease(id, quantity)
        } finally {
            lock.unlock()
        }
    }
}
