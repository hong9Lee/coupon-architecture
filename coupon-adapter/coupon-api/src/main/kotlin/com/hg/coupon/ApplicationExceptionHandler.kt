package com.hg.coupon

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.net.URI

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ApplicationExceptionHandler {

  @ExceptionHandler(value = [Exception::class])
  fun handle(request: HttpServletRequest,
             exception: Exception): ResponseEntity<ProblemDetail> {

    logger.error(exception) { exception.message }

    val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    val problemDetail = problemDetail(httpStatus,
        httpStatus.name,
        request,
        exception)

    return ResponseEntity.status(httpStatus).body(problemDetail)
  }

  fun problemDetail(httpStatus: HttpStatus,
                    code: String,
                    request: HttpServletRequest,
                    exception: Exception): ProblemDetail {
    return problemDetail(httpStatus, code, request, exception.message)
  }

  fun problemDetail(httpStatus: HttpStatus,
                    code: String,
                    request: HttpServletRequest,
                    message: String?): ProblemDetail {
    val problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, message)
    problemDetail.instance = URI.create(request.requestURL.toString())
    problemDetail.setTitle(code)
    problemDetail.setType(URI.create(request.requestURI))

    return problemDetail
  }
}
