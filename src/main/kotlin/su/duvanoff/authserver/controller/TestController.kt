package su.duvanoff.authserver.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    fun test(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.name
    }
}