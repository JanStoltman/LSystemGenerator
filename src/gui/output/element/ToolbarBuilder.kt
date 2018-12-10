package gui.output.element

import java.awt.Dimension

class ToolbarBuilder{
    protected var prefSize: Dimension? = null
    protected var minSize: Dimension? = null

    fun setPreferedSize(dimension: Dimension): ToolbarBuilder {
        this.prefSize = dimension
        return this
    }

    fun setMinSize(dimension: Dimension): ToolbarBuilder{
        this.minSize = dimension
        return this
    }

    fun build(): Toolbar{
        val toolbar = Toolbar()
        toolbar.preferredSize = prefSize
        toolbar.minimumSize = minSize
        return toolbar
    }
}