package ng.apk.vhire.ui.profile

class ProfilePresenter(val mView:ProfileContract.View) :ProfileContract.Presenter{

    override fun start() {
        mView.initView()
    }

    override fun finalize() {

     }
    init {
        mView.presenter = this
    }

}