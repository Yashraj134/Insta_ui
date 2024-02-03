package com.example.jetpackpractice.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackpractice.R
import kotlin.collections.listOf as listOf

@Composable
fun ProfileScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(modifier = Modifier)
        },
        content = { contentPadding ->
            MainContent(modifier = Modifier.padding(contentPadding))
        }
    )
}

@Composable
fun MainContent(modifier: Modifier){
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
   Column(modifier.fillMaxSize()) {
       ProfileScreen()
       Spacer(modifier = Modifier.height(20.dp))
       PostTabView(onTabSelected = {index ->
             selectedTabIndex = index
       })
       when(selectedTabIndex){
           0 -> PostsSection()
       }
   }
}

@Composable
fun PostsSection() {
    val posts = listOf(
        painterResource(id = R.drawable.gdsc_logo),
        painterResource(id = R.drawable.jetpack_camp)
    )
    LazyVerticalGrid(columns = GridCells.Fixed(3),modifier = Modifier.scale(1.01f), content = {
        items(posts.size){
            Image(painter = posts[it], contentDescription = "", contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(
                        width = 1.dp,
                        color = Color.White
                    )
            )
        }
    })
}

@Composable
fun PostTabView(
    modifier: Modifier = Modifier,
    onTabSelected: (selectedIndex: Int) -> Unit
    ) {
        var  selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val tabIcons = listOf(
        TabRowIcons(R.drawable.ic_grid),
        TabRowIcons(R.drawable.ic_reel_icon),
        TabRowIcons(R.drawable.ic_tag_icon)
    )
    TabRow(selectedTabIndex = selectedTabIndex, modifier = modifier, ) {
        tabIcons.forEachIndexed { index, tabRowIcons ->
            Tab(selected = index == selectedTabIndex,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                },
                icon = {
                    Icon(painter = painterResource(id = tabRowIcons.icon), contentDescription = "",
                        modifier.size(20.dp),
                        tint = if(selectedTabIndex == index) Color.Black else Color.Gray
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

@Composable
fun ProfileSection(modifier : Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxWidth()){
        Spacer(modifier = Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
           ImageBuilder(
                  image = painterResource(id = R.drawable.profile_pic),
                  modifier = modifier
                      .size(100.dp)
                      .weight(3f)
           )
            ProfileStatusBar(modifier = Modifier.weight(7f))//status bar beside profile pic
        }
        Spacer(modifier = Modifier.height(8.dp))
        BioSection(
            name = "Walchand College of Engineering ",
            activityLabel = "Education",
            description = "Walchand College of Engineering (WCE : IIT Vishrambag)\nis a well-known engineering institution located in Sangli, Maharashtra.\n It was established in 1947.",
            link = "http://www.walchandsangli.ac.in/",
            followers = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
                append("Followed by ")
                pushStyle(boldStyle)  //for 2 different fonts in a single line
                append("IIT Bombay")
                pop()
                append(", ")
                pushStyle(boldStyle)
                append("Mukesh Ambani")
                pop()
                append(" and")
                pushStyle(boldStyle)
                append(" 27 others")
            }
        )
        Spacer(modifier = Modifier.height(25.dp))
        ButtonSection(modifier = modifier.fillMaxWidth())
        Spacer(modifier = modifier.height(20.dp))
        HighlightSection(
            highlight = listOf(
                StoryHighlights(
                    image = painterResource(id = R.drawable.insta_highlight_discorde),
                    title = "Discord"
                ),
                StoryHighlights(
                    image = painterResource(id = R.drawable.insta_highlight_youtube),
                    title = "Youtube"
                )
            )
        )

    }
}

@Composable
fun HighlightSection(highlight: List<StoryHighlights>, modifier: Modifier = Modifier) {
    LazyRow(modifier.padding(horizontal = 20.dp)){
        items(highlight.size){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.padding(end = 15.dp)) {
                ImageBuilder(image = highlight[it].image, modifier = Modifier.size(70.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = highlight[it].title, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ButtonSection(modifier: Modifier) { //between bio and content
    val miniWidth = 100.dp
    val height = 30.dp
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(horizontal = 20.dp)
    ){
        SampleButton(
            modifier = Modifier
                .defaultMinSize(miniWidth)
                .height(height),
            text = "Following", icon = Icons.Default.KeyboardArrowDown
        )
        SampleButton(
            modifier = Modifier
                .defaultMinSize(miniWidth)
                .height(height),
            text = "Message"
        )
        SampleButton(
            modifier = Modifier
                .defaultMinSize(miniWidth)
                .height(height),
            text = "Email"
        )
        SampleButton(
            modifier = Modifier
                .size(height)
            , icon = Icons.Default.KeyboardArrowDown
        )
    }

}

@Composable
fun SampleButton(
    modifier: Modifier = Modifier,
    text:String? = null,
    icon  :ImageVector? = null) {

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFFE2E1E1))
            .clickable { }
    ) {
        if(text != null) Text(text = text,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis
       )
        if(icon != null) Icon(imageVector = icon, contentDescription = "", tint = Color.Black)
    }

}

@Composable
fun BioSection(
    name: String,
    activityLabel: String,
    description: String,
    link: String,
    followers: AnnotatedString
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)) {

        Text(text = name, fontWeight = FontWeight.Bold,letterSpacing= letterSpacing,lineHeight = lineHeight )
        Text(text = activityLabel, fontWeight = FontWeight.Medium,letterSpacing= letterSpacing,lineHeight = lineHeight, color = Color.Gray)
        Text(text = description, fontWeight = FontWeight.Medium,letterSpacing= letterSpacing,lineHeight = lineHeight)
        Text(text = link, fontWeight = FontWeight.Medium,letterSpacing= letterSpacing,lineHeight = lineHeight, color = Color(0xFF174A72))
        Text(text = followers, fontWeight = FontWeight.Medium,letterSpacing= letterSpacing,lineHeight = lineHeight, fontSize = 13.sp)

    }

}

@Composable
fun ProfileStatusBar(modifier: Modifier) {
      Row(verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceAround,
          modifier = modifier
      ){
          FollowSection(number = "3", label="Posts",modifier= modifier)
          FollowSection(number = "100k", label="Followers",modifier= modifier)
          FollowSection(number = "10", label="Following",modifier= modifier)
      }
}

@Composable
fun FollowSection(number: String, label: String, modifier: Modifier) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
       Text(text = number, fontWeight = FontWeight.Bold, fontSize = 20.sp)
       Spacer(modifier = Modifier.height(4.dp))
       Text(text = label, fontWeight = FontWeight.SemiBold)
    }
}

//for profile image and hollow border beside it
@Composable
fun ImageBuilder(image: Painter,modifier: Modifier) {
    Image(
        painter =  image, contentDescription = "",
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(width = 2.dp, color = Color.LightGray, shape = CircleShape)
            .padding(5.dp)
            .clip(CircleShape)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier) {
    TopAppBar(title = {
        Text(
            text = "mkr.developer", modifier.padding(start = 20.dp, bottom = 6.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier.size(30.dp)
            )
        },
        actions = {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "back",
                    modifier.size(30.dp),
                    tint = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "back",
                    modifier.size(30.dp),
                    tint = Color.Black
                )
            }
        }
    )
}


