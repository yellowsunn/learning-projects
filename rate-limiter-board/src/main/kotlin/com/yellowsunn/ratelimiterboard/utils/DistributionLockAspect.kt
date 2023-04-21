package com.yellowsunn.ratelimiterboard.utils

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Aspect
@Component
class DistributionLockAspect(
    private val redissonClient: RedissonClient,
) {
    @Around("@annotation(distributionLock)")
    fun lockAndUnlock(joinPoint: ProceedingJoinPoint, distributionLock: DistributionLock): Any? {
        val lockName: String = generateLockName(joinPoint, distributionLock)

        val lock: RLock = redissonClient.getLock(lockName)
        try {
            val isAvailable: Boolean = lock.tryLock(
                distributionLock.waitTime,
                distributionLock.leaseTime,
                distributionLock.timeUnit,
            )
            if (isAvailable.not()) {
                throw IllegalStateException("[$lockName] lock 획득 실패.")
            }
            return joinPoint.proceed()
        } finally {
            lock.unlock()
        }
    }

    private fun generateLockName(joinPoint: ProceedingJoinPoint, distributionLock: DistributionLock): String {
        val methodName = getFullMethodName(joinPoint)

        // lockName 이 존재하지 않으면 메소드이름과 args의 조합으로 lockName을 생성
        return distributionLock.lockName.ifBlank {
            val suffix = joinPoint.args?.joinToString(separator = ":") ?: ""
            return "$methodName:$suffix"
        }
    }

    private fun getFullMethodName(joinPoint: ProceedingJoinPoint): String {
        val method: Method = (joinPoint.signature as MethodSignature).method

        return "${joinPoint.target.javaClass.name}.${method.name}"
    }
}
