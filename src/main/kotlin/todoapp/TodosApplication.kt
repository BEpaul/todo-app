package todoapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import todoapp.web.model.SiteProperties

@SpringBootApplication
@ConfigurationPropertiesScan
class TodosApplication

fun main(args: Array<String>) {
	runApplication<TodosApplication>(*args)
}
