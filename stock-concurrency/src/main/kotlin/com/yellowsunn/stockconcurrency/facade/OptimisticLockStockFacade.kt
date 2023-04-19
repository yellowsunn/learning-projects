package com.yellowsunn.stockconcurrency.facade

import com.yellowsunn.stockconcurrency.service.OptimisticLockStockService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import org.springframework.orm.ObjectOptimisticLockingFailureException

@Service
class OptimisticLockStockFacade(
    private val optimisticLockStockService: OptimisticLockStockService,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    fun decrease(id: Long, quantity: Long) {
        while (true) {
            try {
                optimisticLockStockService.decrease(id, quantity)
                break
            } catch (e: ObjectOptimisticLockingFailureException) {
                log.warn("락 점유 실패. id={}", id)
                TimeUnit.MILLISECONDS.sleep(50L)
            }
        }
    }
}
