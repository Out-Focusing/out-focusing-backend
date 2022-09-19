package com.out_focusing.out_focusing_backend.album.api

import com.out_focusing.out_focusing_backend.album.application.AlbumApplication
import com.out_focusing.out_focusing_backend.album.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/albums")
@Tag(name = "Album API")
class AlbumApi(private val albumApplication: AlbumApplication) {

    @Operation(summary = "앨범 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun generateAlbum(@RequestBody @Valid requestBody: GenerateAlbumRequest): GenerateAlbumResponse {
        return albumApplication.generateAlbum(requestBody)
    }

    @Operation(summary = "앨범 삭제")
    @DeleteMapping("/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    fun removeAlbum(@PathVariable @Valid albumId: Long) {
        albumApplication.removeAlbum(albumId)
    }

    @Operation(summary = "앨범 수정")
    @PutMapping("/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateAlbum(@PathVariable @Valid albumId: Long, @RequestBody @Valid requestBody: ModifyAlbumRequest) {
        albumApplication.modifyAlbum(albumId, requestBody)
    }
    
    @Operation(summary = "앨범 북마크")
    @PostMapping("/{albumId}/bookmarks")
    @ResponseStatus(HttpStatus.CREATED)
    fun addAlbumBookmark(@PathVariable albumId: Long) {
        albumApplication.addAlbumBookmark(albumId)
    }

    @Operation(summary = "앨범 북마크 취소")
    @DeleteMapping("/{albumId}/bookmarks")
    @ResponseStatus(HttpStatus.OK)
    fun cancelAlbumBookmark(@PathVariable albumId: Long) {
        albumApplication.cancelAlbumBookmark(albumId)
    }

    @Operation(summary =  "앨범 상세 조회")
    @GetMapping("/{albumId}")
    fun getAlbumDetail(@PathVariable albumId: Long): AlbumDetailResponse {
        return albumApplication.getAlbumDetail(albumId)
    }

    @Operation(summary = "자신이 작성한 앨범 조회")
    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    fun getMyAlbum(pageable: Pageable): List<AlbumSummaryResponse> {
        return albumApplication.getMyAlbum(pageable)
    }

    @Operation(summary = "유저가 작성한 앨범 조회")
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserAlbum(@PathVariable userId: String, pageable: Pageable): List<AlbumSummaryResponse> {
        return albumApplication.getUserAlbum(userId, pageable)
    }

    @Operation(summary = "앨범 조회")
    @GetMapping
    fun searchAlbum(@RequestParam("keyword") keyword: String, pageable: Pageable): List<AlbumSummaryResponse> {
        return albumApplication.searchAlbumByKeyword(keyword, pageable)
    }

    @Operation(summary = "업데이트된 북마크한 앨범 조회")
    @GetMapping("/my/bookmarks/feed")
    fun getUpdatedBookmarkAlbum(): List<AlbumSummaryResponse> {
        return albumApplication.getUpdatedBookmarkAlbum()
    }
}