package course.spring.restmvc.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import course.spring.restmvc.Application;
import course.spring.restmvc.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackageClasses = Application.class)
@Slf4j
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> handle(EntityNotFoundException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
