package com.yusifmammadov.samplechatapp.data

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yusifmammadov.samplechatapp.data.model.Message
import com.yusifmammadov.samplechatapp.data.model.MessageUi
import com.yusifmammadov.samplechatapp.data.model.User
import com.yusifmammadov.samplechatapp.util.Constants
import com.yusifmammadov.samplechatapp.util.Constants.USERS_PATH
import com.yusifmammadov.samplechatapp.util.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.Exception
import kotlin.math.log

private const val TAG = "ChatRepository"


class ChatRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {

    init {
        Log.d(TAG, "init: Repository created")
    }


    fun isUserSignedIn() = auth.currentUser != null

    suspend fun signUpUser(userName: String, email: String, password: String) = flow<Resource<Nothing>> {

        try {
            emit(Resource.Loading())
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid!!
            dbRef.child(USERS_PATH).child(uid).setValue(User(userName, email, uid)).await()
            emit(Resource.Success(null))

        } catch (e: Exception) {
            Log.d(TAG, "signUpUser: exception: ${e.message}")
            emit(Resource.Error("Could not sign up"))
        }
    }

    suspend fun signIn(email: String, password: String) = flow<Resource<Nothing>> {
        try {
            emit(Resource.Loading())
            auth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error("Could not sign in"))
        }
    }

    suspend fun signOut() = flow<Resource<Nothing>> {
        try {
            emit(Resource.Loading())
            auth.signOut()
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error("Could not sign out"))
        }
    }

    suspend fun getUsers() = callbackFlow<Resource<List<User>>> {

        trySend(Resource.Loading())

        val listener = dbRef.child(USERS_PATH).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val listUsers = mutableListOf<User>()

                snapshot.children.forEach { userSnapshot ->
                    val user = userSnapshot.getValue(User::class.java)
                    if (user?.uid!! != auth.currentUser?.uid!!) {
                        listUsers.add(user)
                    }

                }

                trySend(Resource.Success(listUsers))
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ${error.message}")
                trySend(Resource.Error("Could not load users"))
            }
        })

        awaitClose {
            dbRef.removeEventListener(listener)
        }

    }

    suspend fun getMessages(receiverId: String) = callbackFlow<Resource<List<MessageUi>>> {

        trySend(Resource.Loading())

        val currentUserRoom = receiverId + auth.currentUser?.uid!!

        val listener = dbRef.child(Constants.CHATS_PATH)
            .child(currentUserRoom)
            .child(Constants.MESSAGES_PATH).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listMessages = mutableListOf<MessageUi>()

                    snapshot.children.forEach { messageSnapShot ->

                        val message = messageSnapShot.getValue(Message::class.java)
                        Log.d(TAG, "onDataChange: senderId: ${message?.senderId}, userId: ${auth.currentUser?.uid}")
                        val isMine = message?.senderId!! == auth.currentUser?.uid!!
                        Log.d(TAG, "onDataChange: $isMine")
                        listMessages.add(MessageUi(
                            message.message,
                            isMine
                        ))
                    }

                    trySend(Resource.Success(listMessages))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error("Could not load messages"))
                }
            })

        awaitClose {
            dbRef.removeEventListener(listener)
        }
    }

    fun sendMessage(receiverId: String, message: String) {

        val currentUserRoom = receiverId + auth.currentUser?.uid!!
        val receiverRoom = auth.currentUser?.uid!! + receiverId

        dbRef.child("chats")
            .child(currentUserRoom)
            .child("messages")
            .push()
            .setValue(
                Message(
                    message,
                    auth.currentUser?.uid!!
                )
            )
            .addOnSuccessListener {
                dbRef.child("chats")
                    .child(receiverRoom)
                    .child("messages")
                    .push()
                    .setValue(
                        Message(
                            message,
                            auth.currentUser?.uid!!
                        )
                    )
            }
    }

    suspend fun getUsernameForId(userId: String) = flow<String> {
        val snapshot = dbRef.child(Constants.USERS_PATH).child(userId).get().await()
        val username = snapshot.getValue(User::class.java)?.userName

        if (username != null) {
            emit(username)
        }
    }

}