package controller;

import dao.TwitterWord;
import view.TwitterMapVO;

public class TwitterWordBoVOConverter {

	public static TwitterWord getBo(TwitterMapVO vo) {
		TwitterWord bo = new TwitterWord();
		bo.setWord(vo.getKeyword());
		bo.setState(vo.getState());
		bo.setOccurances(vo.getOccurances());
		return bo;
	}
	
	public static TwitterMapVO getVo(TwitterWord bo) {
		TwitterMapVO vo = new TwitterMapVO();
		vo.setKeyword(bo.getWord());
		vo.setState(bo.getState());
		vo.setOccurances(bo.getOccurances());
		return vo;
	}
}
