package likelion.helloworld.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.BeanIsNotAFactoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BeanIsNotAFactoryException.class)
    public ResponseEntity<String> IdNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 회원입니다.");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> JwtSignatue(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> JwtExpire(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("만료된 토큰입니다.");
    }
}
