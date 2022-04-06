package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.post.domain.PostContent
import org.springframework.data.repository.CrudRepository

interface PostContentRepository: CrudRepository<PostContent, Long> {
}