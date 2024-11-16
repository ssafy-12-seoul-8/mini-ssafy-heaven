package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.response.GameDetailResponse;
import java.util.List;

public interface GameService {

  List<GameDetailResponse> getAll();

}
