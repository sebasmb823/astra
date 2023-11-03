package com.app.astratask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class creacionCuenta : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creacion_cuenta)

        val txtUserName: TextView = findViewById(R.id.edtNombre)
        val txtEmailNuevo: TextView = findViewById(R.id.edtEmailUserNuevo)
        val txtPassNuevo: TextView = findViewById(R.id.edtpassNuevo)
        val txtPassNuevo1: TextView = findViewById(R.id.edtpassNuevo1)
        val btnCrear: Button = findViewById(R.id.btnCrearCuentaNueva)

        btnCrear.setOnClickListener(){
            var pass1 = txtPassNuevo.text.toString()
            var pass2 = txtPassNuevo1.text.toString()

            if (pass1.equals(pass2)){
                creacionCuenta(txtEmailNuevo.text.toString(), txtPassNuevo.text.toString())
            }
            else{
                Toast.makeText(baseContext, "Error: Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                txtPassNuevo.requestFocus()
            }
        }
        firebaseAuth = Firebase.auth
    }

    private fun creacionCuenta(email: String, password: String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).
            addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    sendEmailVerification()
                    Toast.makeText(baseContext, "Cuenta creada correctamente, se requiere verificacion", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(baseContext, "Error: " + task.exception, Toast.LENGTH_SHORT).show()
                }

        }
    }

    private fun sendEmailVerification(){
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this) { task ->
            if(task.isSuccessful){


            }else{

            }

        }
    }
}