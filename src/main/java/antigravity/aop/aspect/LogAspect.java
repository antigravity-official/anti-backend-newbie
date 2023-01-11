package antigravity.aop.aspect;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import antigravity.exception.AntigravityException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LogAspect {

	@Around("execution(* antigravity.service..*save*(..))")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		String classSignature = joinPoint.getSignature().getDeclaringType().toString();
		String methodName = joinPoint.getSignature().getName();

		try {
			long startMillis = System.currentTimeMillis();
			log.info("[{}.{}], start={}", classSignature, methodName, new Date());

			Object result = joinPoint.proceed();

			long endMillis = System.currentTimeMillis();

			log.info("[{}.{}], end={}, time taken={}ms",
				classSignature, methodName, new Date(), endMillis - startMillis);

			return result;
		} catch (AntigravityException e) {
			log.error("[{}.{}], message={}", classSignature, methodName, e.getMessage());
			throw e;
		}

	}
}
