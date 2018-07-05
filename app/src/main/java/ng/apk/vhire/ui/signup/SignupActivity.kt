package ng.apk.vhire.ui.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.widget.EditText
import com.google.gson.JsonObject
import es.dmoral.prefs.Prefs
import kotterknife.bindView
import ng.apk.vhire.MainActivity
import ng.apk.vhire.R
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.ui.base.BaseActivity

class SignupActivity : BaseActivity(), SignupContract.View {


    override lateinit var presenter: SignupContract.Presenter

    val firstNameInput: EditText by bindView(R.id.input_first_name)
    val lastNameInput: EditText by bindView(R.id.input_last_name)
    val emailInput: EditText by bindView(R.id.input_email)
    val phoneInput: EditText by bindView(R.id.input_number)
    val passwordInput: EditText by bindView(R.id.input_password)
    val signUpButton: AppCompatButton by bindView(R.id.btn_signup)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        SignupPresenter(this,Http(this@SignupActivity))

        presenter.start()
    }

    override fun initView() {
        signUpButton.setOnClickListener {

            val inputs = listOf(firstNameInput,lastNameInput,emailInput,phoneInput,passwordInput)

            for (field in inputs){
                if (field.text.isNullOrEmpty()){
                    field.error = "Please fill in this field"
                }

            }

            for (field in inputs){
                if (field.text.isNullOrEmpty()){
                   return@setOnClickListener
                }
            }

            val data = JsonObject()
            data.addProperty("first_name",firstNameInput.text.toString())
            data.addProperty("last_name",lastNameInput.text.toString())
            data.addProperty("email",emailInput.text.toString())
            data.addProperty("phone_number",phoneInput.text.toString()[0])
            data.addProperty("password",passwordInput.text.toString())


            presenter.signup(data)
        }

    }
    override fun onSuccessfulsignup(data: JsonObject?) {
        Prefs.with(this@SignupActivity).write("token", data!!["token"].asString)
        Prefs.with(this@SignupActivity).write("role", data["role"].asString)

        startActivity(Intent(this@SignupActivity,MainActivity::class.java))
        showToast("Signup Succesful","success")

        finish()
    }
}
