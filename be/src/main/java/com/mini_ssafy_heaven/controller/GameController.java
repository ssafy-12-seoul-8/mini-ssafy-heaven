package com.mini_ssafy_heaven.controller;

import com.mini_ssafy_heaven.doc.GameDocument;
import com.mini_ssafy_heaven.dto.response.GameDetailResponse;
import com.mini_ssafy_heaven.service.GameService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController implements GameDocument {

  private final GameService gameService;

  @Override
  @GetMapping
  public ResponseEntity<List<GameDetailResponse>> getAll() {
    List<GameDetailResponse> response = gameService.getAll();

    return ResponseEntity.ok(response);
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<GameDetailResponse> get(@PathVariable("id") Long id) {
    GameDetailResponse response = gameService.getById(id);

    return ResponseEntity.ok(response);
  }

}
