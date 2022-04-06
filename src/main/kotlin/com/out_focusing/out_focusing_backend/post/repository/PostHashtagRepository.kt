package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.post.domain.PostHashtag
import org.springframework.data.repository.CrudRepository

interface PostHashtagRepository: CrudRepository<PostHashtag, Long> {
}