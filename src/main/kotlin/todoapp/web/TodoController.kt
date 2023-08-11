package todoapp.web

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.View
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.view.AbstractView
import todoapp.commons.web.view.CommaSeparatedValuesView
import todoapp.core.todo.application.TodoFind
import todoapp.core.todo.domain.Todo
import todoapp.web.convert.TodoToSpreadsheetConverter


import todoapp.web.model.SiteProperties
import java.net.http.HttpHeaders
import java.util.*

@Controller
class TodoController(
    private val siteProperties: SiteProperties,
    private val find: TodoFind
) {
    @ModelAttribute("site")
    fun siteProperties() = siteProperties

    @RequestMapping("/todos")
    fun todos() {
    }

    @RequestMapping(value = ["/todos"], produces = ["text/csv"])
    fun downloadTodos(model: Model) {
        model.addAttribute(TodoToSpreadsheetConverter().convert(find.all()))
    }

    class TodoCsvViewResolver : ViewResolver {
        override fun resolveViewName(viewName: String, locale: Locale): View? {
            return if (viewName == "todos") {
                CommaSeparatedValuesView()
            } else {
                null
            }
        }
    }

    class TodoCsvView : AbstractView(), View {

        private val log = LoggerFactory.getLogger(javaClass)

        init {
            contentType = "text/csv"
        }

        override fun generatesDownloadContent(): Boolean {
            return true
        }

        override fun renderMergedOutputModel(
            model: MutableMap<String, Any>,
            request: HttpServletRequest,
            response: HttpServletResponse
        ) {
            log.info("render model as csv content")

            response.addHeader(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"todos.csv\"")
            response.writer.println("id,title,completed")
            val todos = model.getOrDefault("todos", emptyList<Todo>()) as List<Todo>
            todos.forEach {
                val line = "${it.id}, ${it.title}, ${it.isCompleted}"
                response.writer.println(line)
            }

            response.flushBuffer()
        }
    }
}