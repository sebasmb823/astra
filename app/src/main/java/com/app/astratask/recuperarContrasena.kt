package com.app.astratask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class recuperarContrasena : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasena)

        val txtEmailRecuperar: TextView = findViewById(R.id.txtEmailCambio)
        val btnCambiar: Button = findViewById(R.id.btnEnviarRecuperacion)

        btnCambiar.setOnClickListener() {
            enviarCorreoRecuperacion(txtEmailRecuperar.text.toString())
        }


        firebaseAuth = Firebase.auth
    }
    private fun enviarCorreoRecuperacion(email: String){
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(baseContext, "Correo de recuperacion enviado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(baseContext, "Error: No existe el correo", Toast.LENGTH_SHORT).show()
                //txtEmailRecuperar.requestFocus()
            }
        }
    }
}