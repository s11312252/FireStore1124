package tw.edu.pu.csim.tcyang.firestore1124


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class UserScoreViewModel : ViewModel() {
    private val userScoreRepository = UserScoreRepository()

    var message by mutableStateOf("訊息")
        private set

    // **新增：支援批量新增組員資料**
    fun addUsers(userScores: List<UserScoreModel>) {
        viewModelScope.launch {
            message = userScoreRepository.addUser(userScores)
        }
    }

    // **保留您原有的函式**
    fun addUser(userScore: UserScoreModel) {
        // 在 viewModelScope 中啟動一個協程
        viewModelScope.launch {
            // 呼叫 suspend function，並等待結果
            message = userScoreRepository.addUser(userScore)
        }
    }

    fun updateUser(userScore: UserScoreModel) {
        // 在 viewModelScope 中啟動一個協程
        viewModelScope.launch {
            // 呼叫 suspend function，並等待結果
            message = userScoreRepository.updateUser(userScore)
        }
    }

    fun deleteUser(userScore: UserScoreModel) {
        // 在 viewModelScope 中啟動一個協程
        viewModelScope.launch {
            // 呼叫 suspend function，並等待結果
            message = userScoreRepository.deleteUser(userScore)
        }
    }

    fun getUser(userScore: UserScoreModel) {
        // 在 viewModelScope 中啟動一個協程
        viewModelScope.launch {
            // 呼叫 suspend function，並等待結果
            message = userScoreRepository.getUserScoreByName(userScore)
        }
    }
    fun orderUser() {
        // 在 viewModelScope 中啟動一個協程
        viewModelScope.launch {
            // 呼叫 suspend function，並等待結果
            message = userScoreRepository.orderByScore() // 這裡的 orderByScore 已修改為格式化輸出
        }
    }
}