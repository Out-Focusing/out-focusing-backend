package com.out_focusing.out_focusing_backend.user.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ModifyUserProfileRequest(
    @field:Size(min = 1, max = 20, message = "이름은 1자 이상 20자 이하여야 합니다.")
    val name: String?,
    @field:Size(min = 1, max = 20, message = "연락처는 1자 이상 50자 이하입니다.")
    val contact: String?,
    val profileImage: String?,
    val readme: String?
)