package org.kdea.main;

import java.util.List;

import org.kdea.reviewboard.ReviewService;
import org.kdea.vo.BoardVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

	@Autowired
	ReviewService reviewsvc;
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<BoardVO> getHighCommend() {
		MainDAO dao = sqlSessionTemplate.getMapper(MainDAO.class);
		List<BoardVO> vo = dao.getHighCommend();
		for(int i=0;i<vo.size();i++){
			int first = vo.get(i).getContents().indexOf("<img");
			System.out.println(first);
			int end = vo.get(i).getContents().indexOf("\" />");
			if(first == -1){
				vo.get(i).setPhoto(null);
				if(vo.get(i).getContents().length()>=20){
					vo.get(i).setContents(vo.get(i).getContents().substring(0, 20)+"...");
				}
				if(vo.get(i).getTitle().length()>=15){
					vo.get(i).setTitle(vo.get(i).getTitle().substring(0, 15)+"...");
				}
			}else{
				vo.get(i).setPhoto(vo.get(i).getContents().substring(first,end+4));
				if(vo.get(i).getContents().length()>=20){
					vo.get(i).setContents(vo.get(i).getContents().substring(0, 20)+"...");
				}
				if(vo.get(i).getTitle().length()>=15){
					vo.get(i).setTitle(vo.get(i).getTitle().substring(0, 15)+"...");
				}
			}
		}
		return vo;
	}
}
