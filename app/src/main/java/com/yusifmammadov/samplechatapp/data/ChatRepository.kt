package com.yusifmammadov.samplechatapp.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatRepository {

    fun isUserSignedIn() = Firebase.auth.currentUser != null


}