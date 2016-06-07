package org.kdea.service;

import org.kdea.vo.BoardVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service("BoardValidator")
public class NoticeValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void validate(Object boardVO, Errors error) {
		// TODO Auto-generated method stub
		BoardVO board = (BoardVO) boardVO;
		if(board.getTitle().length() == 0) {
			
			System.out.println("boardvo.content" + board.getContents().length());
			error.rejectValue("title","boardForm.emptytitle");
			
		}
		if(board.getContents().length() == 0) {
			
			error.rejectValue("contents", "boardForm.emptycontents");
			
		}
		
	}
	
}
