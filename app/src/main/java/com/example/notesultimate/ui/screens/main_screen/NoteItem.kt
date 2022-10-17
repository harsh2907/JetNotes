package com.example.notesultimate.ui.screens.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesultimate.domain.model.Note
import com.example.notesultimate.domain.util.DateTimeUtil
import com.example.notesultimate.ui.theme.NotesUltimateTheme
import com.example.notesultimate.ui.theme.Roboto
import com.example.notesultimate.ui.util.Previews

@Composable
fun NoteItem(note:Note,onDelete:(Note)->Unit,onClick:(Note)->Unit) {
  Box(modifier = Modifier
      .padding(12.dp)
      .clickable {
          onClick(note)
      }){
      Column(
          Modifier
              .fillMaxWidth()
              .align(Alignment.TopStart)
              .background(Color(note.color), shape = RoundedCornerShape(14.dp))
              .padding(16.dp)
      ) {
          Text(
              text = note.title,
              fontFamily = Roboto,
              fontWeight = FontWeight.Medium,
              fontSize = 24.sp,
              modifier = Modifier.padding(4.dp),
              maxLines = 2,
              overflow = TextOverflow.Ellipsis,
              color = Color.Black
          )
          Text(
              text = note.content,
              fontFamily = Roboto,
              fontSize = 16.sp,
              fontWeight = FontWeight.Light,
              modifier = Modifier.padding(4.dp),
              maxLines = 8,
              overflow = TextOverflow.Ellipsis,
              color = Color.Black
          )

          Row(
              Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Bottom) {

              Text(
                  text = DateTimeUtil.getTime(note.created),
                  fontFamily = Roboto,
                  fontSize = 16.sp,
                  modifier = Modifier
                      .padding(6.dp),
                  color = Color.Black
              )

              Icon(
                  imageVector = Icons.Default.Delete,
                  contentDescription = "Delete",
                  tint = Color.Black,
                  modifier = Modifier
                      .padding(6.dp)
                      .clickable {
                          onDelete(note)
                      }
              )
          }

      }
  }
}

