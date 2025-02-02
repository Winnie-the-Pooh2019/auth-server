package su.duvanoff.authserver.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import su.duvanoff.authserver.Error

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(
        value = [
            Exception::class
        ]
    )
    fun processException(e: Exception): ResponseEntity<Error> {
        return ResponseEntity(
            Error(
                Error.StatusCode(HttpStatus.I_AM_A_TEAPOT.value(), HttpStatus.I_AM_A_TEAPOT.name),
                e.message
            ), HttpStatus.I_AM_A_TEAPOT
        )
    }
}