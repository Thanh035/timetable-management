<<<<<<< HEAD:src/main/kotlin/com/mycompany/myapp/DemoApplication.kt
package com.mycompany.myapp
=======
package com.exmaple.demo
>>>>>>> quangthanh/dev:src/main/kotlin/com/exmaple/demo/DemoApplication.kt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
