package org.kdea.freeboard;

import java.util.List;

import org.kdea.vo.CommentVO;
import org.kdea.vo.FreeBoardVO;
import org.kdea.vo.SearchVO;

public interface FreeBoardDAO {
   
   public List<FreeBoardVO> list(int page);//����Ʈ �ҷ�����

   public int write(FreeBoardVO fbVO);

   public FreeBoardVO read(String id);

   public FreeBoardVO readNum(int num);//������ ����ҷ�����

   public int cmtWrite(CommentVO comment);//�ڸ�Ʈ�ޱ�

   public List<CommentVO> cmtList(int num);//�ڸ�Ʈ �ҷ�����

   public int modify(FreeBoardVO fb);//�ۼ���

   public List<FreeBoardVO> beforeDelete(int num);//������ ����ִ��� Ȯ��

   public int delete(int num);//�������

   public CommentVO getCommentDetail(int num);//������ ����ҷ�����

   public int cmtModify(CommentVO comment);//�ڸ�Ʈ �������

   public int cmtDelete(int num);//�ڸ�Ʈ ����

   public int relpyInput(FreeBoardVO fbVO);//��� �ޱ�

   public List<FreeBoardVO> getSearchList(SearchVO svo);//�˻�

   public int viewsCtn(int num);//��ȸ�� ī��Ʈ



}