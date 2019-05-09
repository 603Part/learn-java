package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.ReplyDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.ReplyPost;

public class ReplyDaoImpl implements ReplyDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public void addReply(ReplyPost replyPost) {
		String sql = "insert into replypost (postID,userNumber,replyContent,replyTime,userType,starNum) values(?,?,?,?,?,?)";
		Object[] params = { replyPost.getPostID(), replyPost.getUserNumber(), replyPost.getReplyContent(),replyPost.getReplyTime(), replyPost.getUserType(), replyPost.getStarNum()};
		try {
			runner.update(sql, params);
			System.out.println("回帖成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ReplyPost> findTeacherReply(String teacherNumber) {
		String sql = "select * from replypost where userNumber=? and userType=?";
		Object[] params = { teacherNumber, 1 };
		try {
			List<ReplyPost> rList = runner.query(sql, new BeanListHandler<ReplyPost>(ReplyPost.class), params);
			for (ReplyPost replyPost : rList) {
				replyPost.setReplyTime(replyPost.getReplyTime().substring(0, 19));
			}
			return rList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ReplyPost> findMyReply(int studentID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReplyPost> findPostReply(int postID) {
		String sql = "select * from replypost where postID=?";
		try {
			List<ReplyPost> replyPosts = runner.query(sql, new BeanListHandler<ReplyPost>(ReplyPost.class), postID);
			for (ReplyPost replyPost : replyPosts) {
				replyPost.setReplyTime(replyPost.getReplyTime().substring(0, 19));
			}
			return replyPosts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<ReplyPost> findReplyUserNumber(int postID) {
		
		String sql = "select * from replypost where postID=?";
		try {
			List<ReplyPost> replyPosts = runner.query(sql, new BeanListHandler<ReplyPost>(ReplyPost.class), postID);
			for (ReplyPost replyPost : replyPosts) {
				replyPost.setUserNumber(replyPost.getUserNumber().substring(0, 19));
			}
			return replyPosts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int findPostReplyCount(int postID) {
		String sql = "select * from replypost where postID=?";
		try {
			List<ReplyPost> replyPosts = runner.query(sql, new BeanListHandler<ReplyPost>(ReplyPost.class), postID);
			if (replyPosts == null) {
				return 0;
			}
			return replyPosts.size();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ReplyPost findOneTeacherReply(String teacherNumber, int postID) {
		String sql = "select * from replypost where userNumber=? and userType='1' and postID=?";
		Object[] params = { teacherNumber, postID };
		try {
			ReplyPost replyPost = runner.query(sql, new BeanHandler<ReplyPost>(ReplyPost.class), params);
			replyPost.setReplyTime(replyPost.getReplyTime().substring(0, 19));
			return replyPost;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateTeacherReply(String replyContent, int replyID) {
		String sql = "update replypost set replyContent=? where replyID=?";
		Object[] params = { replyContent, replyID };
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteThemeAllRep(int postID) {
		String sql = "delete from replypost where postID=?";
		try {
			runner.update(sql, postID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRep(int replyID) {
		String sql = "delete from replypost where replyID=?";
		try {
			runner.update(sql, replyID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
