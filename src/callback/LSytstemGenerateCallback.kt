package callback

interface LSytstemGenerateCallback {
    fun onGenerated(lSystem: String) {
        print("OnGenerated $lSystem")
    }
}