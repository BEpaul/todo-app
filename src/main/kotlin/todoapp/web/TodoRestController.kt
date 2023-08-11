package todoapp.web

import com.fasterxml.jackson.databind.JsonNode
import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import todoapp.core.todo.application.TodoCleanup
import todoapp.core.todo.application.TodoFind
import todoapp.core.todo.application.TodoModification
import todoapp.core.todo.application.TodoRegistry
import todoapp.core.todo.domain.Todo
import todoapp.core.todo.domain.TodoId

@RestController
@RequestMapping(path = ["/api/todos"])
class TodoRestController(
    private val find: TodoFind,
    private val registry: TodoRegistry,
    private val modification: TodoModification,
    private val cleanup: TodoCleanup
) {

    val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun list(): List<Todo> {
        return find.all()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid command: WriteTodoCommand) {
        log.info("request: $command")

        registry.register(command.title)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @RequestBody @Valid command: WriteTodoCommand) {
        log.info("request id: $id")

        modification.modify(TodoId.of(id), command.title, false)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) {
        log.info("request id: $id")

        cleanup.clear(TodoId.of(id))
    }

    data class WriteTodoCommand(
        @field:Size(min = 4, max = 140)
        val title: String,
        val completed: Boolean
    ){

}
}