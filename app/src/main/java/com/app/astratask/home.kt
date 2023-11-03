package com.app.astratask

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class home : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        firebaseAuth = Firebase.auth

        val btnpomodoro: Button = findViewById(R.id.btnPomodoro)
        val btnCalendario: Button = findViewById(R.id.btnCalendario)

        btnpomodoro.setOnClickListener(){
            val i = Intent(this, pomodoro::class.java)
            startActivity(i)
        }

        btnCalendario.setOnClickListener(){
            val i = Intent(this, calendario::class.java)
            startActivity(i)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.menu_buscar -> {
                Toast.makeText(baseContext, "Buscar info.", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_salir -> {
                logOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        return
    }

    private fun logOut(){
        firebaseAuth.signOut()
        Toast.makeText(baseContext,"Sesion cerrada correctamente", Toast.LENGTH_SHORT).show()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}