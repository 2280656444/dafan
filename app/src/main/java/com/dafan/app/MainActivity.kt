package com.dafan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// 数据模型：包含菜谱名称、分类、时间、网络图片、做法步骤
data class Recipe(
    val id: String,
    val name: String,
    val category: List<String>,
    val time: Int,
    val imageUrl: String,
    val steps: List<String>
)

// 内置菜谱数据池（带真实图片链接）
val allRecipes = listOf(
    Recipe(
        "1", "宫保鸡丁", listOf("全部", "川菜", "午餐"), 25, 
        "https://images.unsplash.com/photo-1564834724105-918b73d1b9e0?w=800", 
        listOf(
            "1. 鸡胸肉切丁，加生抽、淀粉、料酒腌制10分钟。",
            "2. 调碗汁：生抽、醋、糖、淀粉、少许水拌匀备用。",
            "3. 热锅凉油，下干辣椒、花椒炒香，下鸡丁滑熟变色。",
            "4. 倒入碗汁快速翻炒收汁，最后加入炸好的花生米和葱段出锅。"
        )
    ),
    Recipe(
        "2", "清蒸鲈鱼", listOf("全部", "粤菜", "老人", "孕妇"), 20, 
        "https://images.unsplash.com/photo-1599084993091-1cb5c0721cc6?w=800", 
        listOf(
            "1. 鲈鱼洗净两面划刀，抹上料酒和少许盐，腌制5分钟。",
            "2. 盘底铺葱段姜片，放上鱼，鱼身也放些葱姜。",
            "3. 水开后上锅大火蒸8-10分钟，关火虚蒸2分钟。",
            "4. 倒掉盘中腥水，铺上新鲜葱丝，淋上蒸鱼豉油，最后浇上热油激发出香味。"
        )
    ),
    Recipe(
        "3", "番茄炒蛋", listOf("全部", "家常菜", "儿童", "早餐"), 10, 
        "https://images.unsplash.com/photo-1525351484163-7529414344d8?w=800", 
        listOf(
            "1. 番茄切小块，鸡蛋加少许盐打散备用。",
            "2. 热锅多倒点油，下鸡蛋炒至凝固成块，盛出备用。",
            "3. 锅留底油，下番茄翻炒出红沙，加少许糖提鲜。",
            "4. 倒回炒好的鸡蛋，翻炒均匀，撒葱花即可出锅。"
        )
    ),
    Recipe(
        "4", "莲藕排骨汤", listOf("全部", "秋季", "老人"), 60, 
        "https://images.unsplash.com/photo-1547592180-85f173990554?w=800", 
        listOf(
            "1. 排骨冷水下锅焯水，捞出洗净血沫。莲藕去皮切滚刀块。",
            "2. 将排骨、莲藕、几片老姜放入砂锅。",
            "3. 加入足量热水，大火烧开后转小火慢炖1小时。",
            "4. 出锅前加入适量盐调味，撒上葱花即可。"
        )
    ),
    Recipe(
        "5", "凉拌黄瓜", listOf("全部", "夏季", "减脂"), 5, 
        "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800", 
        listOf(
            "1. 黄瓜洗净拍碎，切成小段放入碗中。",
            "2. 加入蒜末、两勺生抽、一勺陈醋、少许白糖和香油。",
            "3. 喜欢吃辣可以加一勺辣椒油。",
            "4. 抓拌均匀，放入冰箱冷藏10分钟口感更佳。"
        )
    ),
    Recipe(
        "6", "葱爆羊肉", listOf("全部", "鲁菜", "冬季"), 15, 
        "https://images.unsplash.com/photo-1602066613789-33879e3206c9?w=800", 
        listOf(
            "1. 羊肉切薄片，用料酒、生抽抓匀腌制。大葱切斜段。",
            "2. 锅烧热多倒油，下羊肉大火快速滑散至变色。",
            "3. 加入大葱段快速翻炒，大葱变软出香味。",
            "4. 顺锅边淋入少许香醋，加盐出锅。"
        )
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 开启沉浸式状态栏（图片和背景延伸到状态栏下方）
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // 定义分类标签
    val categories = listOf("全部", "川菜", "粤菜", "鲁菜", "家常菜", "夏季", "秋季", "冬季", "老人", "孕妇", "儿童")
    var selectedCategory by remember { mutableStateOf("全部") }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    // 根据选中的分类过滤菜品
    val filteredRecipes = if (selectedCategory == "全部") {
        allRecipes
    } else {
        allRecipes.filter { it.category.contains(selectedCategory) }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        topBar = {
            // 顶部标题区（带沉浸式 padding）
            Column(modifier = Modifier.padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 8.dp)) {
                Text(
                    text = "搭饭",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(text = "今天想吃点什么？", fontSize = 14.sp, color = Color.Gray)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // 横向滑动分类标签
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 菜品瀑布流列表
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredRecipes) { recipe ->
                    RecipeCard(recipe = recipe, onClick = { selectedRecipe = recipe })
                }
                // 底部留白
                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }

    // 底部抽屉：点击卡片后弹出详细做法
    if (selectedRecipe != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedRecipe = null },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                // 抽屉顶部小横条
                Box(
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.CenterHorizontally)
                        .width(40.dp)
                        .height(4.dp)
                        .background(Color.LightGray, RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(selectedRecipe!!.name, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("烹饪时间：约 ${selectedRecipe!!.time} 分钟", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("做法步骤", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(12.dp))
                
                // 做法步骤列表
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(selectedRecipe!!.steps) { step ->
                        Text(step, fontSize = 16.sp, lineHeight = 24.sp)
                    }
                    item { Spacer(modifier = Modifier.height(60.dp)) }
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            // 网络图片加载
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                contentScale = ContentScale.Crop
            )
            
            Column(modifier = Modifier.padding(16.dp)) {
                Text(recipe.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                
                // 标签行
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    recipe.category.take(3).forEach {
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                Text("约 ${recipe.time} 分钟", fontSize = 13.sp, color = Color.Gray)
            }
        }
    }
}
