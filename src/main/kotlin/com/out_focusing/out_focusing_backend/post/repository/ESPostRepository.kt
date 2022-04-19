package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.post.domain.ESPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ESPostRepository: ElasticsearchRepository<ESPost, Long> {
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title\", \"hashtag\"]} }")
    fun findByKeyword(word: String, pageable: Pageable): Page<ESPost>
}