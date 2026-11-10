package com.dafan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dish(val name: String, val slot: String, val timeMin: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TodayScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen() {
    var dishes by remember {
        mutableStateOf(
            listOf(
                Dish("小米粥 + 水煮蛋", "早餐", 8),
                Dish("青椒鸡丁 + 西兰花 + 米饭", "午餐", 25),
                Dish("番茄炒蛋 / 蒜蓉生菜 / 紫菜蛋花汤 / 米饭", "晚餐", 20)
            )
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("搭饭 · 今日餐单") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(dishes) { dish ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(dish.slot, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.height(4.dp))
                            Text(dish.name, fontSize = 16.sp)
                            Spacer(Modifier.height(4.dp))
                            Text("约 ${dish.timeMin} 分钟", fontSize = 12.sp)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { dishes = dishes.shuffled() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("换一换")
            }
        }
    }
}
