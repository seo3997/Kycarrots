package com.whomade.kycarrots.controller;

import com.whomade.kycarrots.entity.TbUserSite;
import com.whomade.kycarrots.service.TbUserSiteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloRestController {

    private final TbUserSiteService tbUserSiteService;

    @GetMapping("hello/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a user by their ID")
    public ResponseEntity<TbUserSite> getUserById(@PathVariable Integer id) {
        Optional<TbUserSite> tbUserSite = tbUserSiteService.findById(id);

        if (tbUserSite.isPresent()) {
            return ResponseEntity.ok(tbUserSite.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
