package com.anyview.dao.impl;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.anyview.dao.ScoreDao;

@Repository
public class ScoreDaoImpl extends BaseDaoImpl implements ScoreDao {

	@Override
	public void updateScoreInScoreTable(int sid, int vid, int cid, float score) {
		String sql = "update scoretable set score=? where sid=? and vid=? and cid=?";
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(sql).setFloat(0, score).setInteger(1, sid).setInteger(2, vid).setInteger(3, cid)
				.executeUpdate();
	}

}
