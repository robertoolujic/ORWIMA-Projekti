package com.ferit.robertoolujic.meshgrab

import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ferit.robertoolujic.meshgrab.data.CollectionViewModel
import com.ferit.robertoolujic.meshgrab.data.ModelViewModel
import com.ferit.robertoolujic.meshgrab.data.User
import com.ferit.robertoolujic.meshgrab.data.UserViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun UserScreen(
    navigation : NavController,
    modelViewModel : ModelViewModel,
    userViewModel : UserViewModel,
    collectionViewModel : CollectionViewModel,
    userId :String
){
    val user = userViewModel.usersData.first { it.id == userId }
    val collections = collectionViewModel.collectionsData.filter { it.creatorId == userId  && !it.private}
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
    ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                BackButton(navigation)
            }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(15.dp)
        ){
            Image(painter = rememberAsyncImagePainter(
                model=user.avatar),
                null,
                modifier = Modifier
                    .padding(end=15.dp)
                    .size(75.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primary)
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = user.name.toString(),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    ShareButton(
                        "https://img.icons8.com/?size=100&id=3447&format=png&color=FFFFFF",
                        "Hey, check this user out! " + user.id,
                        LocalContext.current
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(end = 15.dp)
                    ){
                        Text(text = "Following: ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground)
                        Text(text = formatter.format(user.following),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(end = 15.dp)
                    ){
                        Text(text = "Followers: ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground)
                        Text(text = formatter.format(user.followers),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(end = 15.dp)
                    ){
                        Text(text = "Likes: ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground)
                        Text(text = formatter.format(user.likes),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground)
                    }


                }
            }
        }
        Text(text = user.bio,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(horizontal=15.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(10.dp)
        )
        LazyRow(
        ){
            items(collections.size){ index ->
                val collection = collectionViewModel.collectionsData.first{it.id == collections[index].id}
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier= Modifier
                        .padding(15.dp)
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(15.dp)
                        .clickable{
                            navigation.navigate(
                                Routes.getCollectionPath(collection.id)
                            ) }
                ){
                    Image(painter = rememberAsyncImagePainter(
                        collection.gallery[0]),
                        null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(end=15.dp)
                            .size(80.dp)
                            .clip(RoundedCornerShape(25))
                            .background(MaterialTheme.colorScheme.background)
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
                            text = SimpleDateFormat("dd.MM.yyyy hh:mm a", Locale.ENGLISH).format(collection.dateUploaded.toDate()),
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
        }
        Text(text = "Uploaded models:",
            modifier = Modifier
                .padding(15.dp))
        ModelLayout(
            modelViewModel.modelsData.filter {it.creatorId == userId},
            modelViewModel,
            userViewModel,
            collectionViewModel,
            navigation
        )
    }
}