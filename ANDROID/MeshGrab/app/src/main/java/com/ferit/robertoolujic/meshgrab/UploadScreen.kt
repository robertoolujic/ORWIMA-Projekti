package com.ferit.robertoolujic.meshgrab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ferit.robertoolujic.meshgrab.data.Model
import com.ferit.robertoolujic.meshgrab.data.ModelViewModel
import com.google.firebase.Timestamp

@Composable
fun UploadScreen(
    modelViewModel: ModelViewModel,
    creatorId : String,
    navigation : NavController
){
    var itemGallery = remember {mutableStateListOf<String>()}
    var itemFiles = remember {mutableStateListOf<String>()}
    var itemTitle by remember {mutableStateOf("")}
    var itemDescription by remember {mutableStateOf("")}
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .statusBarsPadding()
            .padding(15.dp),
    ) {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            var currentImageIndex by remember {mutableStateOf(0)}
            if(itemGallery.isNotEmpty())
            ImageCarousel(itemGallery,
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .aspectRatio(1.33f)
                    .clip(shape = RoundedCornerShape(15.dp)),
                {index -> currentImageIndex = index}
                )
            else
            Image(
                painter = rememberAsyncImagePainter(
                    model = "https://www.photobookseh.ca/storage/designer/969/pages/12212.jpg?cache=1302618539"
                ),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .aspectRatio(1.33f)
                    .clip(shape = RoundedCornerShape(15.dp))
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                IconButton({itemGallery.add("https://matriarch.tax/wp-content/uploads/2023/09/placeholder-4-3.png")}
                )
                {
                    Icon(painter = rememberAsyncImagePainter("https://img.icons8.com/?size=100&id=60628&format=png&color=FFFFFF"),
                        null,
                        tint =MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(50.dp)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .padding(5.dp)
                    )
                }
                if(!itemGallery.isEmpty())
                    IconButton({itemGallery.removeAt(currentImageIndex)}
                    )
                    {
                        Icon(painter = rememberAsyncImagePainter("https://img.icons8.com/?size=100&id=47258&format=png&color=FFFFFF"),
                            null,
                            tint =MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(50.dp)
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .padding(5.dp)
                        )
                    }
            }

        }
        FileUploadButton(itemFiles, {})
        TextFormField(itemTitle,{ itemTitle = it },"Item title")
        TextFormField(itemDescription,{ itemDescription = it },"Item description")
        IconButton({if(itemTitle!="" && itemDescription!="" && itemGallery.isNotEmpty() && itemFiles.isNotEmpty())
            modelViewModel.addModel(
            Model(
                title = itemTitle,
                description = itemDescription,
                gallery = itemGallery,
                creatorId = creatorId,
                dateUploaded = Timestamp.now(),
                files = itemFiles
            ),
                { modelId ->
                    navigation.navigate(
                        Routes.getModelPath(modelId)
                    )
                })
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(shape=RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(5.dp)) {
            Icon(rememberAsyncImagePainter("https://img.icons8.com/?size=100&id=368&format=png&color=000000"),
                null,
                tint = MaterialTheme.colorScheme.onPrimary
                    )
        }
    }
}
@Composable
fun FileUploadButton(
    files : MutableList<String>,
    onClick : () -> Unit
){
    LazyRow{
        item {
            IconButton({ files.add("https://matriarch.tax/wp-content/uploads/2023/09/placeholder-4-3.png") }
            )
            {
                Icon(
                    painter = rememberAsyncImagePainter("https://img.icons8.com/?size=100&id=11550&format=png&color=FFFFFF"),
                    null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                )
            }
        }
            items(files.size){ index ->
                Box(
                    contentAlignment = Alignment.TopEnd
                )
                {
                    Image(painter = rememberAsyncImagePainter(files[index]),
                        null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(50.dp)
                            .padding(5.dp)
                            .aspectRatio(1.33f)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .clickable{ files.removeAt(index) }
                    )
                }
        }
    }
}
@Composable
fun TextFormField(
    itemText : String,
    onValueChange: (String) -> Unit,
    placeholderText : String
){
    OutlinedTextField(
        value = itemText,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        ),
        placeholder = {
            Text(text=placeholderText,
                color = MaterialTheme.colorScheme.onSecondary)
        },
        trailingIcon = {
            IconButton(onClick = { onValueChange("") }) {
                Icon(
                    painter = rememberAsyncImagePainter("https://img.icons8.com/?size=100&id=66767&format=png&color=FFFFFF"),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(20.dp)
                )
            }
        },
        modifier = Modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth()
    )
}