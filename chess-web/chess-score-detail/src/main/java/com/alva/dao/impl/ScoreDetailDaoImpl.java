package com.alva.dao.impl;

import com.alva.dao.IScoreDetailDao;
import com.alva.dispatcher.db.UpdateWrapper;
import com.alva.dispatcher.exception.SqlBuildException;
import com.alva.entity.ScoreDetail;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-16
 */
public class ScoreDetailDaoImpl implements IScoreDetailDao {

	@Override
	public Integer insert(ScoreDetail scoreDetail) {
		try {
			UpdateWrapper<ScoreDetail> wrapper = new UpdateWrapper<>(ScoreDetail.class);
			return wrapper.insert(scoreDetail).execute();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
