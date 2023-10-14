import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuser.data.database.FavoriteUser
import com.dicoding.mygithubuser.data.database.FavoriteUserDao
import com.dicoding.mygithubuser.data.database.UserDatabase
import com.dicoding.mygithubuser.data.response.DetailUserResponse
import com.dicoding.mygithubuser.data.response.GithubUser
import com.dicoding.mygithubuser.data.retrofit.ApiConfig
import com.dicoding.mygithubuser.data.retrofit.ApiService
import com.dicoding.mygithubuser.ui.detail.DetailUserActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao= userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        val client = ApiConfig.getApiService()
            .getUserDetail(username)
            .enqueue (object : Callback<DetailUserResponse> {
                override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, id:Int, avatarUrl:String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }
    suspend fun checkUser(id: Int)= userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}
