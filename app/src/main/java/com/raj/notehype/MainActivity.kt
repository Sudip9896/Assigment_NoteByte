package com.raj.notehype

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.raj.notehype.database.NoteDatabase
import com.raj.notehype.repository.NoteRepository
import com.raj.notehype.viewmodel.NoteViewModel
import com.raj.notehype.viewmodel.NoteViewModelFactory
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private var id = "947949544794-fjbvntmg8fpm2nd2splibskir8vujqv9.apps.googleusercontent.com"


    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
            //working
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(id)
                .requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val auth = Firebase.auth
        val user = auth.currentUser

        if (user != null) {
           // val userName = user.displayName
           // val usergmail = user.email
            //Toast.makeText(this, "userEmail is" + usergmail, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, " Login Failed", Toast.LENGTH_SHORT).show()
        }


        // caling the function
        setupViewModel()

    }

    private fun setupViewModel() {
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)

        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]


    }
}
