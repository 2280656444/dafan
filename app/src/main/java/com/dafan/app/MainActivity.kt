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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// 定义美食橙主题色
val FoodOrange = Color(0xFFE65100)
val FoodBg = Color(0xFFFDF8F5)

data class Recipe(
    val id: String,
    val name: String,
    val category: List<String>,
    val time: Int,
    val imageUrl: String,
    val steps: List<String>
)

val allRecipes = listOf(
    Recipe("1", "宫保鸡丁", listOf("全部", "川菜", "午餐"), 25, "https://images.unsplash.com/photo-1564834724105-918b73d1b9e0?w=800", listOf("1. 鸡胸肉切丁，加生抽、淀粉、料酒腌制10分钟。", "2. 调碗汁：生抽、醋、糖、淀粉、少许水拌匀备用。", "3. 热锅凉油，下干辣椒、花椒炒香，下鸡丁滑熟变色。", "4. 倒入碗汁快速翻炒收汁，最后加入炸好的花生米和葱段出锅。")),
    Recipe("2", "清蒸鲈鱼", listOf("全部", "粤菜", "老人", "孕妇"), 20, "https://images.unsplash.com/photo-1599084993091-1cb5c0721cc6?w=800", listOf("1. 鲈鱼洗净两面划刀，抹上料酒和少许盐，腌制5分钟。", "2. 盘底铺葱段姜片，放上鱼，鱼身也放些葱姜。", "3. 水开后上锅大火蒸8-10分钟，关火虚蒸2分钟。", "4. 倒掉盘中腥水，铺上新鲜葱丝，淋上蒸鱼豉油，最后浇上热油激发出香味。")),
    Recipe("3", "番茄炒蛋", listOf("全部", "家常菜", "儿童", "早餐"), 10, "https://images.unsplash.com/photo-1525351484163-7529414344d8?w=800", listOf("1. 番茄切小块，鸡蛋加少许盐打散备用。", "2. 热锅多倒点油，下鸡蛋炒至凝固成块，盛出备用。", "3. 锅留底油，下番茄翻炒出红沙，加少许糖提鲜。", "4. 倒回炒好的鸡蛋，翻炒均匀，撒葱花即可出锅。")),
    Recipe("4", "莲藕排骨汤", listOf("全部", "秋季", "老人"), 60, "https://images.unsplash.com/photo-1547592180-85f173990554?w=800", listOf("1. 排骨冷水下锅焯水，捞出洗净血沫。莲藕去皮切滚刀块。", "2. 将排骨、莲藕、几片老姜放入砂锅。", "3. 加入足量热水，大火烧开后转小火慢炖1小时。", "4. 出锅前加入适量盐调味，撒上葱花即可。")),
    Recipe("5", "凉拌黄瓜", listOf("全部", "夏季", "减脂"), 5, "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800", listOf("1. 黄瓜洗净拍碎，切成小段放入碗中。", "2. 加入蒜末、两勺生抽、一勺陈醋、少许白糖和香油。", "3. 喜欢吃辣可以加一勺辣椒油。", "4. 抓拌均匀，放入冰箱冷藏10分钟口感更佳。")),
    Recipe("6", "葱爆羊肉", listOf("全部", "鲁菜", "冬季"), 15, "https://images.unsplash.com/photo-1602066613789-33879e3206c9?w=800", listOf("1. 羊肉切薄片，用料酒、生抽抓匀腌制。大葱切斜段。", "2. 锅烧热多倒油，下羊肉大火快速滑散至变色。", "3. 加入大葱段快速翻炒，大葱变软出香味。", "4. 顺锅边淋入少许香醋，加盐出锅。")),
    Recipe("7", "麻婆豆腐", listOf("全部", "川菜", "午餐"), 15, "https://images.unsplash.com/photo-1600335895229-6e75511892c8?w=800", listOf("1. 豆腐切块，焯水去豆腥味。", "2. 炒香肉末、豆瓣酱、蒜末。", "3. 加适量水，下豆腐煮3分钟。", "4. 分三次勾芡，出锅撒花椒面和葱花。")),
    Recipe("8", "可乐鸡翅", listOf("全部", "家常菜", "儿童"), 30, "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?w=800", listOf("1. 鸡翅两面划刀，冷水下锅焯水。", "2. 锅中热油，将鸡翅煎至两面金黄。", "3. 倒入一听可乐，加生抽、老抽、姜片。", "4. 大火烧开转中小火煮15分钟，最后大火收汁。"))
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 定制主题色，去掉默认的紫色
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = FoodOrange,
                    background = FoodBg,
                    surface = Color.White
                )
            ) {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val categories = listOf("全部", "川菜", "粤菜", "鲁菜", "家常菜", "夏季", "秋季", "冬季", "老人", "孕妇", "儿童")
    var selectedCategory by remember { mutableStateOf("全部") }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    val filteredRecipes = if (selectedCategory == "全部") {
        allRecipes
    } else {
        allRecipes.filter { it.category.contains(selectedCategory) }
    }

    Scaffold(
        containerColor = FoodBg,
        topBar = {
            Column(modifier = Modifier.padding(top = 52.dp, start = 24.dp, end = 24.dp, bottom = 12.dp)) {
                Text(
                    text = "搭饭 🍳",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black,
                    color = FoodOrange
                )
                Text(text = "每天都要好好吃饭呀", fontSize = 14.sp, color = Color.Gray)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // 顶部胶囊分类
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(categories) { category ->
                    val isSelected = selectedCategory == category
                    Surface(
                        onClick = { selectedCategory = category },
                        shape = RoundedCornerShape(50),
                        color = if (isSelected) FoodOrange else Color.White,
                        shadowElevation = if (isSelected) 4.dp else 0.dp
                    ) {
                        Text(
                            text = category,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 沉浸式大图列表
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(filteredRecipes) { recipe ->
                    RecipeCard(recipe = recipe, onClick = { selectedRecipe = recipe })
                }
                item { Spacer(modifier = Modifier.height(30.dp)) }
            }
        }
    }

    // 底部做法弹窗
    val currentRecipe = selectedRecipe
    if (currentRecipe != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedRecipe = null },
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle(color = Color.LightGray) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(currentRecipe.name, fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⏱ 约 ${currentRecipe.time} 分钟", fontSize = 14.sp, color = FoodOrange, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("烹饪步骤", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = FoodOrange)
                Spacer(modifier = Modifier.height(16.dp))
                
                currentRecipe.steps.forEachIndexed { index, step ->
                    Row(modifier = Modifier.padding(bottom = 16.dp)) {
                        // 序号圆圈
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(FoodOrange, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${index + 1}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = step.removePrefix("${index + 1}. ").trim(),
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            lineHeight = 24.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp) // 卡片固定高度，大图模式
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 背景大图
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(Color.LightGray),
                error = ColorPainter(Color.Gray)
            )
            
            // 底部黑色渐变遮罩，让白色文字清晰可见
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 300f
                        )
                    )
            )
            
            // 顶部右上角的时间标签
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White.copy(alpha = 0.85f)
            ) {
                Text(
                    text = "${recipe.time} 分钟",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = FoodOrange,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            // 底部文字信息
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = recipe.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    recipe.category.take(2).forEach {
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
