package com.exmaple.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingPongController {
    companion object {
        private var COUNTER = 0
    }

    data class PingPong(val result: String)

    @GetMapping("/ping")
    fun getPingPong(): PingPong {
        return PingPong("Pong: ${++COUNTER}")
    }

}