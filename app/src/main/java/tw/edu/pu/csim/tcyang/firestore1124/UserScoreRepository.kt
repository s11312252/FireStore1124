package tw.edu.pu.csim.tcyang.firestore1124

import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import android.text.format.DateFormat // **新增匯入：用於日期時間格式化**

class UserScoreRepository {
    val db = Firebase.firestore

    suspend fun addUser(userScore: UserScoreModel): String {
        return try {
            // 為了讓後續的 updateUser/deleteUser 函式能正常工作，建議使用 set() 並以 user 姓名作為文件ID
            db.collection("UserScore")
                .document(userScore.user)
                .set(userScore)
                .await()
            "新增資料成功！Document ID:\n ${userScore.user}"
        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "新增資料失敗：${e.message}"
        }
    }

    suspend fun updateUser(userScore: UserScoreModel): String {
        return try {
            db.collection("UserScore")
                .document(userScore.user)
                .set(userScore)
                .await()
            "新增/異動資料成功！Document ID:\n ${userScore.user}"
        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "新增/異動資料失敗：${e.message}"
        }
    }

    suspend fun deleteUser(userScore: UserScoreModel): String {
        return try {
            // 1. 取得文件參考
            val documentRef = db.collection("UserScore").document(userScore.user)

            // 2. 執行讀取操作，確認文件是否存在
            val documentSnapshot = documentRef.get().await()

            if (documentSnapshot.exists()) {
                // 3. 如果文件存在，才執行刪除
                documentRef.delete().await()
                "刪除資料成功！Document ID: ${userScore.user}"
            } else {
                // 4. 如果文件不存在，回傳對應的訊息
                "刪除失敗：Document ID ${userScore.user} 不存在。"
            }

        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "刪除資料失敗：${e.message}"
        }
    }

    suspend fun getUserScoreByName(userScore: UserScoreModel): String {
        return try {
            var userCondition = userScore.user // 建議使用傳入的 userScore.user
            val querySnapshot = db.collection("UserScore")
                .whereEqualTo("user", userCondition) // 篩選條件
                .get().await()
            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.first() // 取得第一個符合條件的文件
                val userScore = document.toObject<UserScoreModel>()
                "查詢成功！${userScore?.user} 的分數是 ${userScore?.score}"
            } else {
                "查詢失敗：找不到使用者 $userCondition 的資料。"
            }
        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "查詢資料失敗：${e.message}"
        }

    }

    // **修改：查詢前三名並格式化輸出 (包含名次、姓名、分數、日期時間)**
    suspend fun orderByScore(): String {
        return try {
            val querySnapshot = db.collection("UserScore")
                .orderBy("score", Query.Direction.DESCENDING) // 分數遞減排序
                .limit(3).get().await() // 限制前三名

            var resultList = mutableListOf<String>()

            // 使用 forEachIndexed 迴圈遍歷，以便取得名次
            querySnapshot.documents.forEachIndexed { index, document ->
                // 將文件轉換為 UserScoreModel
                val userScore = document.toObject<UserScoreModel>()

                userScore?.let {
                    // 格式化日期時間
                    val formattedTime = it.timestamp?.let { date ->
                        DateFormat.format("yyyy/MM/dd HH:mm:ss", date).toString()
                    } ?: "無時間戳"

                    val rank = index + 1 // 名次
                    // 格式化輸出：名次. 姓名, 分數, 存入日期時間
                    val line = "$rank. 姓名: ${it.user}, 分數: ${it.score}, 存入時間: $formattedTime"
                    resultList.add(line)
                }
            }

            return if (resultList.isNotEmpty()){
                "🏆 查詢前三名 (分數遞減排序)：\n" + resultList.joinToString("\n")
            } else {
                "抱歉，資料庫目前無相關資料"
            }

        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "❌ 查詢資料失敗：${e.message}"
        }
    }
}