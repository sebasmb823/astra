package com.app.astratask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btningresar : Button = findViewById(R.id.btnIngresar)
        val txtemail: TextView = findViewById(R.id.edtEmail)
        val txtpass: TextView = findViewById(R.id.edtPassword)
        val btnCrearCuentaNueva: TextView = findViewById(R.id.btnCrearCuenta)
        val btnRecordar: TextView = findViewById(R.id.btnRecuperarContraseña)


        firebaseAuth = Firebase.auth
        btningresar.setOnClickListener(){
            signIN(txtemail.text.toString(),txtpass.text.toString())
        }

        btnCrearCuentaNueva.setOnClickListener(){
            val i = Intent(this, creacionCuenta:: class.java)
            startActivity(i)
        }

        btnRecordar.setOnClickListener(){
            val i = Intent(this, recuperarContrasena::class.java)
            startActivity(i)
        }

    }

    private fun signIN(email: String, password: String)
    {
       firebaseAuth.signInWithEmailAndPassword(email, password)
           .addOnCompleteListener (this){ task ->
           if(task.isSuccessful)  {
               val user = firebaseAuth.currentUser
               val verificacion = user?.isEmailVerified

               if(verificacion == true){
                   Toast.makeText(baseContext, "Ingreso Exitoso",Toast.LENGTH_SHORT).show()
                   //Activando la segunda activity home
                   val i = Intent(this, home:: class.java)
                   startActivity(i)
               }else{
                   Toast.makeText(baseContext, "No ha verificado su correo electronico", Toast.LENGTH_SHORT).show()
               }
           }
           else{
               Toast.makeText(baseContext, "Error de Email ò Contraseña", Toast.LENGTH_SHORT).show()
           }
           }
    }
}