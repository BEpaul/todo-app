package todoapp.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import todoapp.web.model.SiteProperties

@Controller
class TodoController (
    private val siteProperties: SiteProperties,
) {
    @ModelAttribute("site")
    fun siteProperties() = siteProperties

    @RequestMapping("/todos")
    fun todos() {
    }
}