package common.mvp

interface Presenter {
    fun bindView(view: View)
    fun unbindView()
}