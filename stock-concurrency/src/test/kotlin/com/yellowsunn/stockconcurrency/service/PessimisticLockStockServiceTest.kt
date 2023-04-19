package com.yellowsunn.stockconcurrency.service

import com.yellowsunn.stockconcurrency.domain.Stock
import com.yellowsunn.stockconcurrency.repository.StockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
class PessimisticLockStockServiceTest {
    @Autowired
    lateinit var stockRepository: StockRepository

    @Autowired
    lateinit var stockService: PessimisticLockStockService

    var stockId: Long = 0L

    @BeforeEach
    fun setUp() {
        val defaultStock = Stock(productId = 1L, quantity = 100L)
        stockRepository.saveAndFlush(defaultStock)
        stockId = defaultStock.id
    }

    @AfterEach
    fun tearDown() {
        stockRepository.deleteAll()
    }

    @Test
    fun 동시에_100개_요청_비관적락으로_정상작동() {
        // given
        val threadCount = 100
        val executorService: ExecutorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        // when
        repeat(threadCount) {
            executorService.submit {
                try {
                    stockService.decrease(stockId, 1L)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        // then
        val stock = stockRepository.findByIdOrNull(stockId)!!
        assertThat(stock.quantity).isEqualTo(0L)
    }
}
