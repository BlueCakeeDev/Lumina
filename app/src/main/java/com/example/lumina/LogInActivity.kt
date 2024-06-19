package com.example.lumina

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    // Declaración de variables para Google SignIn
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        // Configuración del botón de inicio de sesión de Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Creación del cliente de inicio de sesión de Google
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Obtención del botón de inicio de sesión desde el diseño
        val signInButton: SignInButton = findViewById(R.id.sign_in_button)

        // Configuración del listener del botón de inicio de sesión
        signInButton.setOnClickListener {
            signIn() // Método para iniciar sesión con Google
        }
    }

    // Método para iniciar sesión con Google
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Método para manejar el resultado del inicio de sesión
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Verificación del código de solicitud
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task) // Manejo del resultado del inicio de sesión
        }
    }

    // Método para manejar el resultado del inicio de sesión
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            if (account != null) {
                // Inicio de sesión exitoso, navegar a MainActivity y pasar datos del usuario
                auth = FirebaseAuth.getInstance()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("user_name", account.displayName)
                intent.putExtra("user_email", account.email)

                startActivity(intent)
                finish() // Finalizar la actividad actual
            } else {
                // Inicio de sesión fallido
                Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            // Error al iniciar sesión
            Log.w("LogInActivity", "signInResult:failed code=${e.statusCode}")
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }
}
