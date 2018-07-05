package ng.apk.vhire.ui.profile


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import es.dmoral.prefs.Prefs
import kotterknife.bindView

import ng.apk.vhire.R
import ng.apk.vhire.ui.base.BaseFragment
import ng.apk.vhire.ui.login.LoginActivity
import ng.apk.vhire.ui.signup.SignupActivity


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment(),ProfileContract.View {
    override lateinit var presenter: ProfileContract.Presenter


        override fun initView() {

            Log.wtf("called",intent)
            if(intent.equals("login")){
                signinButton.setOnClickListener({
                    e->
                    startActivity(Intent(this@ProfileFragment.context,LoginActivity::class.java))
                })

                signupButton.setOnClickListener({
                    e->
                    startActivity(Intent(this@ProfileFragment.context,SignupActivity::class.java))

                })
            }

         }

    var intent:String? = null
    val signupButton: Button by bindView(R.id.signup)
    val signinButton: Button by bindView(R.id.signin)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if(!Prefs.with(context!!).read("role","not").equals("not")) {
            intent = "profile"
            return inflater.inflate(R.layout.fragment_profile, container, false)
        } else{
            intent = "login"
            return inflater.inflate(R.layout.layout_no_user, container, false)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ProfilePresenter(this)
        presenter.start()

    }

    companion object {

        fun newInstance(): ProfileFragment {

            return ProfileFragment()
        }
    }

}
