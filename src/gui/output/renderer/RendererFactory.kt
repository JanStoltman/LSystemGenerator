package gui.output.renderer

class RendererFactory {
    companion object {
        fun getRenderer(viewType: Int = -1) = when (viewType) {
            0 -> {
                OrthoRenderer(OrthoRenderer.VIEW_TOP)
            }
            1 -> {
                OrthoRenderer(OrthoRenderer.VIEW_LEFT)
            }
            2 -> {
                OrthoRenderer(OrthoRenderer.VIEW_FRONT)
            }
            else -> {
                PerspectiveRenderer()
            }
        }

        fun getRenderer() = getRenderer(-1)
    }
}