package com.alva.controller;

import com.alva.annotaion.Dispatcher;
import com.alva.annotaion.PageResponse;
import com.alva.annotaion.RequestHandler;
import com.alva.dispatcher.entity.Page;
import com.alva.entity.ScoreDetail;
import com.alva.service.IScoreDetailService;
import com.alva.service.impl.ScoreDetailServiceImpl;

import javax.servlet.annotation.WebServlet;


/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-17
 */
@Dispatcher
@WebServlet("/scoreDetail")
public class ScoreDetailController {

	private final IScoreDetailService scoreDetailService = new ScoreDetailServiceImpl();

	@PageResponse(value = ScoreDetail.class, eqSql = "user_id = ?", eqBy = "userId")
	@RequestHandler("/page")
	public void page(Page<ScoreDetail> page){
	}

}
