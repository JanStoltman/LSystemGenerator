package gui.output.element

import gui.output.renderer.RendererFactory

class ViewportFactory {
    companion object {
        fun getViewport(rendererType: Int, label: String) =
                Viewport(RendererFactory.getRenderer(rendererType), label)

        fun getViewport(label: String) = getViewport(-1, label)
    }
}