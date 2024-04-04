package com.android.chakkiwallah.presentation.homescreen.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.chakkiwallah.R
import com.android.chakkiwallah.presentation.ui.theme.Shapes
import com.android.chakkiwallah.presentation.ui.theme.inter
import org.w3c.dom.Text

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)){
    Row(modifier= modifier.weight(1f)){
       Image( painter = painterResource(id =  R.drawable.joni),
           contentDescription ="",
           modifier = modifier
               .size(56.dp)
               .clip(shape = CircleShape))
        Spacer(modifier = modifier.width(16.dp))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
           Text(text = "Good Evening",
               style = MaterialTheme.typography.h2.copy(color = Color.Gray,
               fontWeight = FontWeight.Light),
               fontFamily = inter,
               fontSize = 15.sp)
            Text(text = "Joni Sharma",
                style = MaterialTheme.typography.h2.copy(color = Color.Gray,
                    fontWeight = FontWeight.Light),
                fontFamily = inter,
                fontSize = 15.sp)
        }
        Spacer(modifier = modifier.width(96.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = R.drawable.bag),
                contentDescription = "")

        }
    }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchBar() {
    val label = "Search"
    var value : String = ""
    TextField(
        value = value, onValueChange = { value },
        label = { Text(text = "Search") },
        shape = Shapes.medium.copy(
            topStart = CornerSize(20.dp),
            topEnd = CornerSize(20.dp),
            bottomEnd = CornerSize(20.dp),
            bottomStart = CornerSize(20.dp)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),

        )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Categories(items : List<Cat>) {
    Column(modifier = Modifier.padding(20.dp)){
        Row{
          Text(text = "Categories")
            Spacer(modifier = Modifier.width(200.dp))

                Text(text = "See All")

        }
        LazyRow() {
            items(items){
               CatItems(it = it)
                Spacer(modifier = Modifier.width(5.dp))
             }
        }
    }
}


@Composable
fun CatItems(it : Cat) {
    Column{
        Image(painter = it.image,
            contentDescription = "",
            modifier = Modifier.size(50.dp))
        Text(text = it.title)
    }
}
data class Cat(
    val title : String,
    val image : Painter
)



@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    imageVector: ImageVector? = null,
    tint: Color = Color.Unspecified
) {
    icon?.let {
        Icon(painter = painterResource(id = it), contentDescription = null, modifier=modifier, tint=tint)
    }
    imageVector?.let {
        Icon(imageVector = it, contentDescription = null,modifier=modifier, tint=tint)
    }
}

@Preview
@Composable
private fun TopAppPreview() {
    val items : List<Cat> = listOf(
        Cat(title = "title", image = painterResource(id = R.drawable.home)),
        Cat(title = "title", image = painterResource(id = R.drawable.home)),
        Cat(title = "title", image = painterResource(id = R.drawable.home)),
        Cat(title = "title", image = painterResource(id = R.drawable.home)),
        Cat(title = "title", image = painterResource(id = R.drawable.home)))
    Column {
        TopBar()
        Spacer(modifier = Modifier.height(35.dp))
        SearchBar()
        Categories(items = items)
    }
}