package com.ferit.robertoolujic.meshgrab

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ferit.robertoolujic.meshgrab.data.CollectionViewModel
import com.ferit.robertoolujic.meshgrab.data.ModelViewModel
import com.ferit.robertoolujic.meshgrab.data.Tab
import com.ferit.robertoolujic.meshgrab.data.UserViewModel
import com.ferit.robertoolujic.meshgrab.ui.theme.MeshGrabTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modelViewModel by viewModels<ModelViewModel>()
        val userViewModel by viewModels<UserViewModel>()
        val collectionViewModel by viewModels<CollectionViewModel>()
        setContent {
            MeshGrabTheme {
                val navigation = rememberNavController()
                var activeTab by remember {mutableStateOf(0)}
                var current = navigation.currentBackStackEntryAsState().value?.destination?.route
                Scaffold(
                    bottomBar = {
                        if (!(current==Routes.SCREEN_MODEL
                            || current == Routes.SCREEN_USER || current == Routes.SCREEN_COLLECTION))
                        Box(
                            modifier = Modifier
                                .dropShadow(RoundedCornerShape(5.dp), shadow = Shadow(5.dp))
                        ) {
                            BottomNavigator(
                                IconPosition.TOP,
                                Color.Transparent,
                                MaterialTheme.colorScheme.primary,
                                Color.Transparent,
                                MaterialTheme.colorScheme.onTertiary,
                                listOf(
                                    Tab(
                                        "https://img.icons8.com/?size=100&id=z6m63h25vYs2&format=png&color=FFFFFF",
                                        "Home"
                                    ),
                                    Tab(
                                        "https://img.icons8.com/?size=100&id=k3VIMLgX0A6W&format=png&color=FFFFFF",
                                        "Upload"
                                    ),
                                    Tab(
                                        "https://img.icons8.com/?size=100&id=15265&format=png&color=FFFFFF",
                                        "Profile"
                                    )
                                ),
                                activeTab,
                                onClick = listOf(
                                    {
                                        activeTab = 0
                                        navigation.popBackStack()
                                        navigation.navigate(
                                            Routes.SCREEN_HOME
                                        )
                                    },
                                    {
                                        activeTab = 1
                                        navigation.popBackStack()
                                        navigation.navigate(
                                            Routes.SCREEN_UPLOAD
                                        )
                                    },{
                                        activeTab = 2
                                        navigation.popBackStack()
                                        navigation.navigate(
                                            Routes.getProfilePath("aqecIWECEbTSH1odriz9")
                                        )
                                    }),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.tertiary)
                                    .padding(5.dp)
                                    .navigationBarsPadding()
                            )
                        }
                    }
                    ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                    ) {
                        Navigation(navigation,
                            modelViewModel,
                            userViewModel,
                            collectionViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigator(
    iconPosition: IconPosition,
    activeContainerColor: Color,
    activeTextColor: Color,
    inactiveContainerColor: Color,
    inactiveTextColor: Color,
    tabs : List<Tab>,
    active : Int,
    onClick: List<() -> Unit>,
    modifier : Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, tab ->
            TabButton(
                iconPosition,
                activeContainerColor,
                activeTextColor,
                inactiveContainerColor,
                inactiveTextColor,
                Modifier,
                tabs[index].iconResource,
                tabs[index].label,
                active == index,
                { onClick[index]() }
            )
        }
    }
}

