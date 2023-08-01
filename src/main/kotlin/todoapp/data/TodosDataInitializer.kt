package todoapp.data

import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import todoapp.core.todo.domain.Todo
import todoapp.core.todo.domain.TodoIdGenerator
import todoapp.core.todo.domain.TodoRepository

@Component
@ConditionalOnProperty(name = ["todoapp.data.initialize"], havingValue = "true")
class TodosDataInitializer (
    private val todoIdGenerator: TodoIdGenerator,
    private val todoRepository: TodoRepository
) : InitializingBean, ApplicationRunner, CommandLineRunner {

    override fun afterPropertiesSet() {
        // InitializingBean
        todoRepository.save(Todo.create("Task One", todoIdGenerator))
    }

    override fun run(args: ApplicationArguments?) {
        // ApplicationRunner
        todoRepository.save(Todo.create("Task Two", todoIdGenerator))
    }

    override fun run(vararg args: String?) {
        // CommandLineRunner
        todoRepository.save(Todo.create("Task Three", todoIdGenerator))
    }
}