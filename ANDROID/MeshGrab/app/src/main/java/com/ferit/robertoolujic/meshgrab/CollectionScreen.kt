package com.ferit.robertoolujic.meshgrab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ferit.robertoolujic.meshgrab.data.Collection
import com.ferit.robertoolujic.meshgrab.data.CollectionViewModel
import com.ferit.robertoolujic.meshgrab.data.Model
import com.ferit.robertoolujic.meshgrab.data.ModelViewModel
import com.ferit.robertoolujic.meshgrab.data.UserViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CollectionScreen(
    navigation : NavController,
    modelViewModel : ModelViewModel,
    userViewModel : UserViewModel,
    collectionViewModel: CollectionViewModel,
    collectionId :String
){
    val collection = collectionViewModel.collectionsData.first { it.id == collectionId }
    val user  = userViewModel.usersData.find { it.id == collection.creatorId }
    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BackButton(navigation)
                ShareButton(
                    "https://img.icons8.com/?size=100&id=3447&format=png&color=FFFFFF",
                    "Hey, check this collection out! " + collection.id,
                    LocalContext.current
                )
            }
        }
        item {
            ImageCarousel(collection.gallery,
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .aspectRatio(4/3f)
                    .clip(shape = RoundedCornerShape(15.dp)),
                {})
        }
        item {
                Column() {
                    Text(
                        text = collection.title,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp)
                            .padding(bottom = 5.dp)
                    ) {
                        Text(
                            text = "Likes: ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = formatter.format(collection.likes),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .padding(end = 5.dp)
                        )
                        var isLiked by remember {mutableStateOf(false)}
                        SaveButton(
                            if(isLiked)"https://img.icons8.com/?size=100&id=59805&format=png&color=FFFFFF"
                            else "https://img.icons8.com/?size=100&id=85038&format=png&color=FFFFFF",
                            {
                                isLiked = !isLiked
                            })
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp)
                            .padding(bottom = 5.dp)
                    ) {
                        Text(
                            text = "Downloads: ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = formatter.format(collection.downloads),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .padding(end = 5.dp)
                        )
                        var isInCollection by remember { mutableStateOf(false) }
                        SaveButton(
                            if (isInCollection) "https://img.icons8.com/?size=100&id=59740&format=png&color=FFFFFF"
                            else "https://img.icons8.com/?size=100&id=KY8KFlMXWpIG&format=png&color=FFFFFF",
                            {
                                isInCollection = !isInCollection
                            })
                    }
                }
        }
        item{
            LazyRow(
            ){
                if(collection.models.isEmpty())item {
                    Text("This collection is empty!")
                }
                else
                items(collection.models.size){ index ->
                    val model = modelViewModel.modelsData.first{it.id == collection.models[index]}
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier= Modifier
                            .padding(15.dp)
                            .clip(shape = RoundedCornerShape(25.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(15.dp)
                            .clickable{
                            navigation.navigate(
                                Routes.getModelPath(model.id)
                            ) }
                    ){
                        Image(painter = rememberAsyncImagePainter(
                            model.gallery[0]),
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
                                text = model.title,
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .width(200.dp)
                            )
                            Text(
                                text = SimpleDateFormat("dd.MM.yyyy hh:mm a", Locale.ENGLISH).format(model.dateUploaded.toDate()),
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
        }

        item{
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clip(shape = RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(15.dp)
                    .clickable{
                        navigation.navigate(
                            Routes.getUserPath(user?.id)
                        )
                    }
            ){
                Image(painter = rememberAsyncImagePainter(
                    model=user?.avatar),
                    null,
                    modifier = Modifier
                        .padding(end=15.dp)
                        .size(50.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primary)
                )
                Column() {
                    Text(
                        text = user?.name.toString(),
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
        item {
            Text(
                text = collection.description,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(10.dp)
            )
        }
    }
}