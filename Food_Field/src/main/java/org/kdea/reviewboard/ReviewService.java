package org.kdea.reviewboard;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.kdea.vo.BoardVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; // 설정파일에 빈으로 등록되었기 때문에 생성자나 Setter 없이 자동으로 주입
	
	public List<BoardVO> getList(int page){
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		return dao.getList(page);
	}

	public String write(BoardVO vo) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		JSONObject jobj = new JSONObject();
		boolean result = false;
		if(dao.write(vo)>0){
			result=true;
		}
		jobj.put("ok", result);
		
		return jobj.toJSONString();
	}

	public BoardVO read(HttpServletRequest request) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		return dao.read(Integer.parseInt(request.getParameter("num")));
	}

	public int pagecount() {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		return dao.page();
	}

	public List<Integer> boardPage(int nowPage) {
		ReviewDAO dao = sqlSessionTemplate.getMapper(ReviewDAO.class);
		int fullPage = dao.page();
		List<Integer> pageList = new ArrayList<Integer>();
		if(nowPage>10){
			String tmp = Integer.toString(nowPage).substring(0,Integer.toString(nowPage).length()-1)+0;	// 앞자리+0
			int firstPage = Integer.parseInt(tmp);
			for(int i=1;i<=10;i++){
				if(firstPage+i>fullPage) break;
				pageList.add(firstPage+i);
			}
		}else{
			if(fullPage>10){
				for(int i=1;i<=10;i++){
					pageList.add(i);
				}
			}else if(fullPage<=10){
				for(int i=1;i<=nowPage;i++){
					pageList.add(i);
				}
			}
		}
		return pageList;
	}

}
