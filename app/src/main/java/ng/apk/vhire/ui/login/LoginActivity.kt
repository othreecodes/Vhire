package ng.apk.vhire.ui.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.JsonObject
import es.dmoral.prefs.Prefs
import kotlinx.android.synthetic.main.activity_login.*
import kotterknife.bindView
import ng.apk.vhire.MainActivity
import ng.apk.vhire.R
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.ui.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity(),LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter


    val inputEmail:EditText by bindView(R.id.input_email)
    val inputPassword:EditText by bindView(R.id.input_password)
    val loginButton: AppCompatButton by bindView(R.id.btn_login)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginPresenter(this, Http(this))
        this.presenter.start()

    }



    override fun initView() {
        Log.e("called","noo")
        loginButton.setOnClickListener {
            e->
            showLoadingDialog("Logging In")
            this.presenter.loginUser(inputEmail.text.toString(),inputPassword.text.toString()).enqueue(
                    object :Callback<JsonObject>{
                        override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                            hideLoadingDialog {
                                    Toast.makeText(this@LoginActivity,"An error occured while making the request",Toast.LENGTH_LONG)
                                            .show()

                            }
                        }

                        override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                            hideLoadingDialog {

                                if(response!!.isSuccessful){

                                    val response = response.body()
                                    Toast.makeText(applicationContext,"You're logged in as ${inputEmail.text}",Toast.LENGTH_LONG)
                                            .show()

                                    Prefs.with(this@LoginActivity).write("token", response!!["token"].asString)
                                    Prefs.with(this@LoginActivity).write("role", response["role"].asString)
                                    Log.e("error",response.toString())
                                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                                    finish()

                                }else{
                                    Toast.makeText(applicationContext,"email or password invalid",Toast.LENGTH_LONG)
                                            .show()
                                }
                            }

                         }

                    }
            )

        }

    }
}
