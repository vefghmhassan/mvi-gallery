package com.vegh.mvisample.data

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.vegh.mvisample.domain.MediaItem
import com.vegh.mvisample.utils.generateThumbnailFromUri
import com.vegh.mvisample.utils.getThumbnail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepository {
    suspend fun loadMediaBeforeDate(
        context: Context,
        beforeDate: Long,
        pageSize: Int
    ): List<MediaItem> = withContext(Dispatchers.IO) {

        val list = mutableListOf<MediaItem>()
        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.DATE_ADDED
        )

        val selection = ("(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR " +
                "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?) AND " +
                "${MediaStore.Files.FileColumns.DATE_ADDED}<?")

        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString(),
            beforeDate.toString()
        )

        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

        val queryUri = MediaStore.Files.getContentUri("external")

        context.contentResolver.query(queryUri, projection, selection, selectionArgs, sortOrder)?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val typeCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE)
            val dateCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)

            while (cursor.moveToNext() && list.size < pageSize) {
                val id = cursor.getLong(idCol)
                val type = cursor.getInt(typeCol)
                val date = cursor.getLong(dateCol)

                val uri = when (type) {
                    MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE -> ContentUris.withAppendedId(imageUri, id)
                    MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO -> ContentUris.withAppendedId(videoUri, id)
                    else -> continue
                }
                val isVideo = type == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO

                val thumbnail = generateThumbnailFromUri(context, uri,isVideo)

                list.add(
                    MediaItem(
                        uri = uri,
                        isVideo = type == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO,
                        dateAdded = date, bitmap = thumbnail
                    )
                )
            }
        }

        return@withContext list
    }

}

