package common

interface Presenter {
    fun bindView(view: View)
    fun unbindView()
}