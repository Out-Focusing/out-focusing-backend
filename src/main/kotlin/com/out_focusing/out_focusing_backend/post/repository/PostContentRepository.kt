package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.post.domain.PostContent
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PostContentRepository: CrudRepository<PostContent, Long> {
}