package callback

interface GraphicsGenerateCallback{
    fun onModelGenerated(vertexCount: Long = 0, facesCount: Long = 0)
}