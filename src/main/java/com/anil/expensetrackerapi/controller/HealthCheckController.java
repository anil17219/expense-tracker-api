package com.anil.expensetrackerapi.controller;

import com.anil.expensetrackerapi.entity.VersionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<VersionModel> version(){
        VersionModel vm = new VersionModel();
        vm.setVersion("1.0");
        return new ResponseEntity<VersionModel>(vm, HttpStatus.OK);
    }

}
