package com.yellowsunn.stockconcurrency.repository

import com.yellowsunn.stockconcurrency.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Long>
