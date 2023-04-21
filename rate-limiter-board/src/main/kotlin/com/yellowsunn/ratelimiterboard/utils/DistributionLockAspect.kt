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
    @Around("@annotation(DistributionLock)")
    fun lockAndUnlock(joinPoint: ProceedingJoinPoint): Any? {
        val method: Method = (joinPoint.signature as MethodSignature).method
        val distributionLock: DistributionLock = method.getAnnotation(DistributionLock::class.java)

        val methodName = getMethodName(joinPoint, method)
        val lockName: String = generateLockName(distributionLock, methodName, joinPoint.args)

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

    private fun generateLockName(distributionLock: DistributionLock, methodName: String, args: Array<Any>?): String {
        return distributionLock.lockName.ifBlank {
            val suffix = args?.joinToString(separator = ":") {
                it.toString()
            } ?: ""
            return "$methodName:$suffix"
        }
    }

    private fun getMethodName(joinPoint: ProceedingJoinPoint, method: Method): String {
        return "${joinPoint.target.javaClass.name}.${method.name}"
    }
}
