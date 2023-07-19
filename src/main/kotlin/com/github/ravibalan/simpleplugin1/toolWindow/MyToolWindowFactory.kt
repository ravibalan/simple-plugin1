package com.github.ravibalan.simpleplugin1.toolWindow

import com.github.ravibalan.simpleplugin1.MyBundle
import com.github.ravibalan.simpleplugin1.services.MyProjectService
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import java.awt.Desktop
import java.awt.FlowLayout
import java.net.URI
import javax.swing.JButton
import javax.swing.JPanel


class MyToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), "JSON-Visualizer", false)

        // Create the content panel for the custom tool window
        val panel = JPanel()
        panel.layout = FlowLayout()

        // Create the JButton for opening the webpage
        val openWebpageButton = JButton("Open JSON VIsualizer")

        // Add an ActionListener to the button to open the webpage
        openWebpageButton.addActionListener {
            try {
                Desktop.getDesktop().browse(URI("https://www.google.com"))
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        // Add the button to the panel
        panel.add(openWebpageButton)

        val content2 = ContentFactory.getInstance().createContent(panel ,"JSON-Visualizer", false)
        toolWindow.contentManager.addContent(content2)

    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val label = JBLabel(MyBundle.message("randomLabel", "?"))

            add(label)
            add(JButton(MyBundle.message("shuffle")).apply {
                addActionListener {
                    label.text = MyBundle.message("randomLabel", service.getRandomNumber())
                }
            })
        }
    }
}
