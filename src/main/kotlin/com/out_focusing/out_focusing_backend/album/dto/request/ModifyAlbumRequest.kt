package com.out_focusing.out_focusing_backend.album.dto.request

import javax.validation.constraints.Size

data class ModifyAlbumRequest(
    @field:Size(min = 1, max = 40, message = "타이틀의 길이가 1에서 40 사이여야 합니다")
    val title: String?,
    @field:Size(max = 300, message = "소개글의 길이가 300 이하여야 합니다")
    val content: String?,
    val isSecret: Boolean?
)
