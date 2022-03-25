package com.out_focusing.out_focusing_backend.album.api

import com.out_focusing.out_focusing_backend.album.application.AlbumApplication
import com.out_focusing.out_focusing_backend.album.dto.GenerateAlbumRequest
import com.out_focusing.out_focusing_backend.album.dto.GenerateAlbumResponse
import com.out_focusing.out_focusing_backend.album.dto.ModifyAlbumRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/album")
@Tag(name = "앨범")
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
    fun removeAlbum(@PathVariable albumId: Long) {
        albumApplication.removeAlbum(albumId, )
    }

    @Operation(summary = "앨범 수정")
    @PutMapping("/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateAlbum(@PathVariable @Valid albumId: Long, @RequestBody @Valid requestBody: ModifyAlbumRequest) {
        albumApplication.modifyAlbum(albumId, requestBody)
    }

}