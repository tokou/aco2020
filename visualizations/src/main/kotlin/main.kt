import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.TagConsumer
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.*
import org.w3c.dom.HTMLElement

fun main() {
    window.onload = {
        document.body?.append {
            buildHeader(emptyList())
            buildMain("Hello, JS!")
        }
    }
}

private fun TagConsumer<HTMLElement>.buildMain(title: String) { main() {
    article {
        div {
            id = "content"
            h2 {
                id = "title"
                +"--- $title ---"
            }
        }
    }
} }

private fun TagConsumer<HTMLElement>.buildHeader(menu: List<Pair<String, () -> Unit>>) { header {
    div {
        h1("title-global") {
            a("/") { +"Advent of Code" }
        }
        nav {
            ul {
                menu.forEach { (title, handler) ->
                    li {
                        a("#") {
                            onClickFunction = { handler() }
                            +"[${title}]"
                        }
                    }
                }
            }
        }
    }
} }
