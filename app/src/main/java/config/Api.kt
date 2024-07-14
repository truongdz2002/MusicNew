package config

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage

object Api {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val storage = Firebase.storage
    val firestore = FirebaseFirestore.getInstance()
}