package com.out_focusing.out_focusing_backend.global.error

import org.springframework.http.HttpStatus

open class CustomException(val code: HttpStatus, override val message: String) : RuntimeException() {

    open class BadRequestException(message: String = "잘못된 요청입니다") : CustomException(HttpStatus.BAD_REQUEST, message)
    open class UnauthorizedException(message: String = "인증되지 않았습니다.") : CustomException(HttpStatus.UNAUTHORIZED, message)
    open class ForbiddenException(message: String = "권한이 없습니다.") : CustomException(HttpStatus.FORBIDDEN, message)
    open class NotFoundException(message: String = "존재하지 않는 리소스입니다.") : CustomException(HttpStatus.NOT_FOUND, message)
    open class ConflictException(message: String = "리소스가 중복되었습니다.") : CustomException(HttpStatus.CONFLICT, message)



    object WrongGrantTypeException : BadRequestException("지원하지 않는 grant type 입니다.")

    object TokenTimeExpiredException : UnauthorizedException("토큰 기한이 만료되었습니다.")
    object WrongTokenException : UnauthorizedException("변조된 토큰입니다.")
    object FailedLoginException : UnauthorizedException("로그인에 실패하였습니다.")
    object UserNotExistsException : UnauthorizedException("로그인 정보가 존재하지 않습니다.")

    object AlbumDeleteForbiddenException : ForbiddenException("앨범 삭제 권한이 없습니다.")
    object AlbumUpdateForbiddenException : ForbiddenException("앨범 수정 권한이 없습니다.")
    object PostDeleteForbiddenException: ForbiddenException("게시글 삭제 권한이 없습니다.")
    object PostUpdateForbiddenException : ForbiddenException("게시글 수정 권한이 없습니다.")

    object UserNotFoundException : NotFoundException("존재하지 않는 유저입니다.")
    object AlbumNotFoundException : NotFoundException("존재하지 않은 앨범입니다.")
    object AlbumBookmarkNotFoundException: NotFoundException("북마크에 추가되지 않은 앨범입니다.")
    object PostNotFoundException : NotFoundException("존재하지 않는 게시글입니다.")
    object PostBookmarkNotFoundException: NotFoundException("북마크에 추가되지 않은 게시글입니다.")

    object UserIdConflictException : ConflictException("중복된 유저 아이디입니다.")
    object AlreadyAlbumBookmarkedException: ConflictException("이미 북마크에 추가한 앨범입니다.")
    object AlreadyPostBookmarkedException: ConflictException("이미 북마크에 추가한 게시글입니다.")

}