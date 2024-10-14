package com.crypto.tranaction.exception

import com.crypto.commons.dto.ErrorResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionController {
    private val logger: Logger = LoggerFactory.getLogger(ExceptionController::class.java)

    @ExceptionHandler(TransactionException::class)
    fun handleTransactionException(
        ex: TransactionException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message ?: "Transaction Failed",
            details = request.getDescription(false)
        )
        logger.error(errorResponse.message, ex)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = "An unexpected error occurred",
            details = request.getDescription(false)
        )
        logger.error(errorResponse.message, ex)
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
