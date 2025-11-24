package tw.edu.pu.csim.tcyang.firestore1124

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun UserScoreScreen( userScoreViewModel: UserScoreViewModel = viewModel()
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        var score by remember { mutableStateOf("") }
        OutlinedTextField(
            value = score,
            onValueChange = { score = it },
            label = { Text("分數") },
            placeholder = { Text("請輸入您的分數") },
            keyboardOptions = KeyboardOptions
                (keyboardType = KeyboardType.Number)
        )
        Text("您的分數是：$score")
        Spacer(modifier = Modifier.size(10.dp))

        // **修改：將單筆新增替換為批量新增組員資料**
        Button(onClick = {
            // **請將以下資料替換成您組員的真實姓名及學號後兩碼分數**
            val teamScores = listOf(
                UserScoreModel(user = "鄭姿佳", score = 95),
                UserScoreModel(user = "林彣媞", score = 90),
                UserScoreModel(user = "陳芯霈", score = 96), // <-- 請修改成您的姓名和分數
                UserScoreModel(user = "黃婉玲", score = 95)
            )
            userScoreViewModel.addUsers(teamScores) // 呼叫新增的批量函式
        }) {
            Text("新增組員資料 (批量)")
        }

        // 保留原有的函式呼叫，但將按鈕文字修改，避免與新功能混淆
        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("陳芯霈", 39)
            userScoreViewModel.addUser(userScore)
        }) {
            Text("新增單筆資料 (原功能)")
        }
        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("陳芯霈", 21)
            userScoreViewModel.updateUser(userScore)
        }) {
            Text("新增/異動資料 (原功能)")
        }

        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("陳芯霈", 21)
            userScoreViewModel.deleteUser(userScore)
        }) {
            Text("刪除資料 (原功能)")
        }

        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("陳芯霈", 21)
            userScoreViewModel.getUser(userScore)
        }) {
            Text("查詢資料 (原功能)")
        }

        // 保持此按鈕名稱不變，但背後邏輯已修改以格式化輸出
        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            userScoreViewModel.orderUser()
        }) {
            Text("查詢前三名")
        }
        Text(userScoreViewModel.message)

    }
}