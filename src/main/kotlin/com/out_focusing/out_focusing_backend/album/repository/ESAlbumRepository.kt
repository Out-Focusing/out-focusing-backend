package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.ESAlbum
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ESAlbumRepository: ElasticsearchRepository<ESAlbum, Long> {
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title\", \"content\"]} }")
    fun findByKeyword(word: String, pageable: Pageable): Page<ESAlbum>
}