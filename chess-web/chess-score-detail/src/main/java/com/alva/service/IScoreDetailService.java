package com.alva.service;

import com.alva.dispatcher.entity.Response;
import com.alva.entity.ScoreDetail;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-16
 */
public interface IScoreDetailService {

	Response<String> create(ScoreDetail scoreDetail);
}
