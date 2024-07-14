import android.content.Intent
import android.content.IntentSender
import config.Api
import com.example.myappmusic.login.SignInResult
import com.example.myappmusic.login.User
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException


class AuthService(
    private  val onTapClient: SignInClient
    ) {

  suspend fun signIn() : IntentSender?{
      val result=try {
       onTapClient.beginSignIn(
                   buildSignRequest()
       ).await()
      }
      catch (e :Exception)
      {
        e.printStackTrace()
          if(e is CancellationException)  throw  e
          null
      }
      return result?.pendingIntent?.intentSender
  }



 suspend fun getSignInResultFromIntent(intent: Intent): SignInResult {
    val credential=onTapClient.getSignInCredentialFromIntent(intent)
    val googleToken=credential.googleIdToken
    val googleAuthCredentials= GoogleAuthProvider.getCredential(googleToken,null)
    return try {
     val user= Api.auth.signInWithCredential(googleAuthCredentials).await().user
        SignInResult(
            data = user?.run {
                User(
                    userId = uid,
                    userName = displayName.toString(),
                    profilePictureUrl = photoUrl?.toString()

                    )
            },
            errorMessage = null
        )
    }
    catch (e :Exception)
    {
      if(e is CancellationException) throw e
        SignInResult(
            data = null,
            errorMessage = e.message
        )
    }

}
     fun signOut(){
       try {
        onTapClient.signOut()
           Api.auth.signOut()
       }
       catch (e:Exception){
           e.printStackTrace()
           if(e is CancellationException) throw  e
       }
    }

   private fun buildSignRequest(): BeginSignInRequest {
       return BeginSignInRequest.builder()
           .setGoogleIdTokenRequestOptions(
               BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                   .setSupported(true)
                   .setFilterByAuthorizedAccounts(false)
                   .setServerClientId("168030752600-eptq08ljusja3klsf5dmj26osscj4jgb.apps.googleusercontent.com")
                   .build()
           )
           .setAutoSelectEnabled(true)
           .build()
   }
    fun uploadUserToFirestore(user: User) {
        Api.firestore.collection("users")
            .document(user.userId) // Sử dụng userId làm document ID
            .set(user)
            .addOnSuccessListener {
                // Upload thành công
                println("Upload user ${user.userId} thành công")
            }
            .addOnFailureListener { e ->
                // Upload thất bại
                println("Upload user ${user.userId} thất bại: ${e.message}")
            }
    }

     fun fetchUserName(onResult: (String) -> Unit) {
        val user = Api.auth.currentUser


        user?.let {
            Api.firestore.collection("users").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        onResult(document.getString("userName") ?: "No Name")
                    } else {
                        onResult("No Document")
                    }
                }
                .addOnFailureListener { exception ->
                    onResult("Error: ${exception.message}")
                }
        } ?: run {
            onResult("No User Logged In")
        }
    }

}




