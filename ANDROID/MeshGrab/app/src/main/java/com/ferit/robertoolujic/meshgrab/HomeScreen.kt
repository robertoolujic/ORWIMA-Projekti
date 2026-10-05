package com.ferit.robertoolujic.meshgrab

import android.content.Intent
import android.icu.text.CompactDecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ferit.robertoolujic.meshgrab.data.Collection
import com.ferit.robertoolujic.meshgrab.data.CollectionViewModel
import com.ferit.robertoolujic.meshgrab.data.Model
import com.ferit.robertoolujic.meshgrab.data.ModelViewModel
import com.ferit.robertoolujic.meshgrab.data.Tab
import com.ferit.robertoolujic.meshgrab.data.User
import com.ferit.robertoolujic.meshgrab.data.UserViewModel
import java.text.SimpleDateFormat
import java.util.Locale

val formatter = CompactDecimalFormat.getInstance(
    Locale.ENGLISH,
    CompactDecimalFormat.CompactStyle.SHORT)
@Composable
fun HomeScreen(
    navigation : NavController,
    modelViewModel : ModelViewModel,
    userViewModel : UserViewModel,
    collectionViewModel : CollectionViewModel,
    userId : String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .statusBarsPadding()
    ){
        var layoutSelector by remember { mutableIntStateOf(0) }
        var searchString by remember { mutableStateOf("") }
        var searchQuery by remember { mutableStateOf("") }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(R.drawable.banner),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
            SearchBar(searchString, {searchString = it}, {searchQuery = searchString})
        }
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .clip(shape = RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
            TabNavigator(
                    IconPosition.TOP,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onSecondary,
                    listOf(
                        Tab(
                            "https://img.icons8.com/?size=100&id=V4Ku63FofJd9&format=png&color=FFFFFF",
                            "Models"
                        ),
                        Tab(
                            "https://img.icons8.com/?size=100&id=15265&format=png&color=FFFFFF",
                            "Users"
                        ),
                        Tab(
                            "https://img.icons8.com/?size=100&id=82790&format=png&color=FFFFFF",
                            "Collections"
                        )
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    onClick = listOf(
                        {
                            layoutSelector = 0
                        },
                        {
                            layoutSelector = 1
                        },
                        {
                            layoutSelector = 2
                        }
                    )
                )
        }
        TabNavigator(
            IconPosition.LEFT,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.onTertiary,
            listOf(
                Tab(
                    "https://img.icons8.com/?size=100&id=HZ2ukyyEDgOj&format=png&color=FFFFFF",
                    "Popular"
                ),
                Tab(
                    "https://img.icons8.com/?size=100&id=85038&format=png&color=FFFFFF",
                    "Most likes"
                ),
                Tab(
                    "https://img.icons8.com/?size=100&id=83159&format=png&color=FFFFFF",
                    "Most downloads"
                ),
                Tab(
                    "https://img.icons8.com/?size=100&id=104&format=png&color=FFFFFF",
                    "Most makes"
                ),
                Tab(
                    "https://img.icons8.com/?size=100&id=91644&format=png&color=FFFFFF",
                    "Recent"
                )
            ),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            onClick = listOf(
                {
                    modelViewModel.modelsData.sortByDescending { it.likes }
                    userViewModel.usersData.sortByDescending { it.likes }
                    collectionViewModel.collectionsData.sortByDescending { it.likes }
                },
                {
                    modelViewModel.modelsData.sortByDescending { it.likes }
                    userViewModel.usersData.sortByDescending { it.likes }
                    collectionViewModel.collectionsData.sortByDescending { it.likes }
                },
                {
                    modelViewModel.modelsData.sortByDescending { it.downloads }
                    userViewModel.usersData.sortByDescending { it.downloads }
                    collectionViewModel.collectionsData.sortByDescending { it.downloads }
                },
                {
                    modelViewModel.modelsData.sortByDescending { it.makes }
                    userViewModel.usersData.sortByDescending { it.makes }
                    collectionViewModel.collectionsData.sortByDescending { it.likes }
                },
                {
                    modelViewModel.modelsData.sortByDescending { it.dateUploaded }
                    userViewModel.usersData.sortByDescending { it.registrationDate }
                    collectionViewModel.collectionsData.sortByDescending { it.dateUploaded }
                }
            ),
        )
        if(layoutSelector == 1)
        UserLayout(
            userViewModel.usersData.filter {it.name.contains(searchQuery, ignoreCase = true)},
            navigation
        )
        else if(layoutSelector == 2)
            CollectionLayout(
                collectionViewModel.collectionsData.filter { !it.private && it.title.contains(searchQuery, ignoreCase = true) },
                navigation
            )
        else
            ModelLayout(
                modelViewModel.modelsData.filter{it.title.contains(searchQuery, ignoreCase = true)},
                modelViewModel,
                userViewModel,
                collectionViewModel,
                navigation
            )
        }
}
@Composable
fun SearchBar(
    searchQuery : String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        ),
        placeholder = {
            Text(text="Search...")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription=null
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),

        keyboardActions = KeyboardActions(
            onSearch = {
                keyboard?.hide()
                onSubmit()
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    )
}
enum class IconPosition{
    TOP, BOTTOM, LEFT, RIGHT
}
@Composable
fun TabButton(
    iconPosition: IconPosition,
    activeContainerColor: Color,
    activeTextColor: Color,
    inactiveContainerColor: Color,
    inactiveTextColor: Color,
    modifier : Modifier,
    iconResource : String,
    text : String,
    isActive : Boolean,
    onClick : () -> Unit
){
    Button(
        colors = if (isActive) ButtonDefaults.buttonColors(
            activeContainerColor,
            activeTextColor)
        else ButtonDefaults.buttonColors(
            inactiveContainerColor,
            inactiveTextColor),
        onClick = { onClick() },
        modifier = modifier
    ){
        val items : List<@Composable () -> Unit> = if (iconPosition == IconPosition.BOTTOM ||
            iconPosition== IconPosition.RIGHT)
        {
            listOf(
                {if(text!="")Text(
                    text = text
            )},
            {if(iconResource!="")Icon(
                    painter = rememberAsyncImagePainter(model = iconResource),
                    contentDescription = null
                )})
        }
        else {
            listOf(
                {if(iconResource!="")Icon(
                    painter = rememberAsyncImagePainter(model = iconResource),
                    contentDescription = null
                )},
                {if(text!="")Text(
                    text = text
                )}
            )
        }
        if(iconPosition == IconPosition.TOP ||
            iconPosition == IconPosition.BOTTOM){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items[0]()
                items[1]()
            }
        }
        else{
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                items[0]()
                items[1]()
            }
        }
    }
}

@Composable
fun TabNavigator(
    iconPosition: IconPosition,
    activeContainerColor: Color,
    activeTextColor: Color,
    inactiveContainerColor: Color,
    inactiveTextColor: Color,
    tabs : List<Tab>,
    onClick: List<() -> Unit>,
    modifier : Modifier
){
    var active by remember { mutableStateOf(0)}
    LazyRow{
        items(tabs.size){ index ->
            TabButton(
                iconPosition,
                activeContainerColor,
                activeTextColor,
                inactiveContainerColor,
                inactiveTextColor,
                modifier,
                tabs[index].iconResource,
                tabs[index].label,
                active == index,
                {
                    onClick[index]()
                    active = index
                }
                )
        }
    }
}

@Composable
fun SaveButton(
    image : String,
    onClick: () -> Unit
){
    Button(
        shape = CircleShape,
        contentPadding = PaddingValues(),
        modifier = Modifier
            .size(30.dp),
        onClick = {onClick()}
    ){
        Image(painter = rememberAsyncImagePainter(
            image),
            null,
        modifier = Modifier
            .padding(5.dp))
    }
}
@Composable
fun ShareButton(
    image : String,
    shareMessage : String,
    context : android.content.Context
){
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(Intent.EXTRA_TEXT, shareMessage)
    intent.type = "text/plain"
    SaveButton(image, {context.startActivity(Intent.createChooser(intent,"Share To:"))})
}
@Composable
fun modelLikeButton(
    model: Model,
    modelViewModel: ModelViewModel,
    userViewModel : UserViewModel,
    collectionViewModel: CollectionViewModel
){
    val likedModels = collectionViewModel.collectionsData.first{
        it.creatorId == "aqecIWECEbTSH1odriz9" && it.title.contains("Liked Models")}.id
    val isLiked = collectionViewModel.IsInCollection(likedModels, model.id)
    SaveButton(
        if(isLiked)"https://img.icons8.com/?size=100&id=59805&format=png&color=FFFFFF"
        else "https://img.icons8.com/?size=100&id=85038&format=png&color=FFFFFF",
        {
            if(isLiked){
                collectionViewModel.RemoveModel(likedModels,model.id)
                modelViewModel.removeLike(model.id)
                userViewModel.removeLike(model.creatorId)
            }
            else{
                collectionViewModel.AddModel(likedModels,model.id)
                modelViewModel.addLike(model.id)
                userViewModel.addLike(model.creatorId)

            }
        })
}
@Composable
fun ModelContainer(
    model : Model,
    modelViewModel: ModelViewModel,
    userViewModel : UserViewModel,
    collectionViewModel : CollectionViewModel,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(400.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable{
                onClick()
            }
    ){
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model=model.gallery[0]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1.33f)
                    .clip(shape=RoundedCornerShape(5.dp))
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(5.dp)
            ){
                Text(
                    color = MaterialTheme.colorScheme.onTertiary,
                    text = model.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(LocalConfiguration.current.screenWidthDp.dp-150.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ){
                    Text(text = formatter.format(model.likes),
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(start = 5.dp))
                    modelLikeButton(model,modelViewModel, userViewModel, collectionViewModel)
                }
            }
        }
    }
}
@Composable
fun UserContainer(
    user : User,
    onClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier= Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(15.dp)
            .clickable{
                onClick()
            }
    ){
        Image(painter = rememberAsyncImagePainter(
            model=user.avatar),
            null,
            modifier = Modifier
                .padding(end=15.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.primary)
        )
        Column() {
            Text(
                text = user.name,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(200.dp)
            )
            Text(
                text = "Joined " + SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(user.registrationDate.toDate()),
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(200.dp)
            )
        }
    }
}
@Composable
fun CollectionContainer(
    collection: Collection,
    onClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier= Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(15.dp)
            .clickable{
                onClick()
            }
    ){
        Image(painter = rememberAsyncImagePainter(
            model=collection.gallery[0]),
            null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end=15.dp)
                .size(80.dp)
                .clip(RoundedCornerShape(25))
                .background(MaterialTheme.colorScheme.primary)
        )
        Column() {
            Text(
                text = collection.title,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(200.dp)
            )
            Text(
                text = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(collection.dateUploaded.toDate()),
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(200.dp)
            )
        }
    }
}

@Composable
fun ModelLayout(
    models : List<Model>,
    modelViewModel: ModelViewModel,
    userViewModel : UserViewModel,
    collectionViewModel: CollectionViewModel,
    navigation : NavController
){
    LazyColumn{
        items(models.size){ index ->
            ModelContainer(
                models[index],
                modelViewModel,
                userViewModel,
                collectionViewModel)
            {
                navigation.navigate(
                    Routes.getModelPath(models[index].id)
                )
            }
        }
    }
}

@Composable
fun UserLayout(
    users : List<User>,
    navigation : NavController
){
    LazyColumn{
        items(users.size){ index ->
            UserContainer(
                users[index])
            {
                navigation.navigate(
                    Routes.getUserPath(users[index].id)
                )
            }
        }
    }
}
@Composable
fun CollectionLayout(
    collections : List<Collection>,
    navigation : NavController
){
    LazyColumn{
        items(collections.size){ index ->
            CollectionContainer(
                collections[index])
            {
                navigation.navigate(
                    Routes.getCollectionPath(collections[index].id)
                )
            }
        }
    }
}