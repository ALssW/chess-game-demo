package com.alva.service.impl;

import com.alva.dao.IScoreDetailDao;
import com.alva.dao.impl.ScoreDetailDaoImpl;
import com.alva.dispatcher.entity.Response;
import com.alva.entity.ScoreDetail;
import com.alva.service.IScoreDetailService;
import com.alva.utils.Logger;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-16
 */
public class ScoreDetailServiceImpl implements IScoreDetailService {
	Logger<ScoreDetailServiceImpl> logger = new Logger<>(ScoreDetailServiceImpl.class);

	private final IScoreDetailDao scoreDetailDao = new ScoreDetailDaoImpl();

	@Override
	public Response<String> create(ScoreDetail scoreDetail) {
		if (scoreDetailDao.insert(scoreDetail) != 1) {
			logger.info("新增流水失败");
			return Response.fail("新增流水失败");
		}
		logger.info("新增流水成功");
		return Response.ok("新增流水成功");
	}

}
