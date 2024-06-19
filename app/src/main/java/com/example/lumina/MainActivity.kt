package com.example.lumina

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.lumina.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var auth: FirebaseAuth // Declarar FirebaseAuth aquí

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Obtener los datos del Intent enviado desde LogInActivity
        val userName = intent.getStringExtra("user_name")
        val userEmail = intent.getStringExtra("user_email")

        // Recibir el FirebaseAuth del intent
        auth = intent.getParcelableExtra("auth") ?: FirebaseAuth.getInstance()

        // Configurar el ViewModel de usuario
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.setUserInfo(userName, userEmail)

        // Configurar NavigationView con el NavController
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        val textView: TextView = headerView.findViewById(R.id.textView)

        // Observar cambios en el nombre de usuario y actualizar la UI
        userViewModel.userName.observe(this, { name ->
            textView.text = name
        })

        val drawerLayout = binding.drawerLayout
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Configurar AppBarConfiguration y NavController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_inicio, R.id.nav_peliculas, R.id.nav_series, R.id.nav_trailers
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.signOut() // Cerrar sesión utilizando FirebaseAuth
    }
}