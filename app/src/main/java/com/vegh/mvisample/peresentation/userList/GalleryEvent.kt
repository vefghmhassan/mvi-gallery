package com.vegh.mvisample.peresentation.userList

import com.vegh.mvisample.domain.User

sealed  class GalleryEvent {
object  LoadUsers: GalleryEvent()
    data class  UsersLoaded(val users: List<User>): GalleryEvent()
    data class Error(val message: String) : GalleryEvent()

}