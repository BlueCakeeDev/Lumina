package com.example.lumina.models

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthSingleton {
    private var auth: FirebaseAuth? = null

    fun getInstance(): FirebaseAuth {
        if (auth == null) {
            auth = FirebaseAuth.getInstance()
        }
        return auth!!
    }
}
