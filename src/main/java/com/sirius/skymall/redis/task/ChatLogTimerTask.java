package com.sirius.skymall.redis.task;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sirius.skymall.redis.vo.AppLog;

@Component
public class ChatLogTimerTask {
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Gson gson = new Gson();

	// 每5分钟执行一次
	
	//@Scheduled(cron = "0/5 * *  * * ?")
	// 每5秒执行一次
	@Scheduled(cron="0 0/10 * * * ?")//每5分钟执行一次
	public void saveChatLog() throws SQLException {
		try {
			String sql = "select voyage_id from voyage_info";
			List<Map<String,Object>> rsLs= jdbcTemplate.queryForList(sql);
			String voyageId = null;
			if(rsLs!=null&&rsLs.size()>0){
				voyageId = (String)rsLs.get(0).get("voyage_id");
			}
			sql = "insert into app_log(voyage_id,from_user,to_user,content,create_time) values('"+voyageId+"',?,?,?,?)";
			ListOperations<String, String> listOperations = redisTemplate
					.opsForList();
			final List<String> logList = listOperations.range("chatlogs", 0, 9999);
			listOperations.trim("chatlogs", 10000, -1);
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					try {
						AppLog appLog = gson.fromJson(logList.get(i), AppLog.class);
						ps.setString(1, appLog.getFromUser());
						ps.setString(2, appLog.getToUser());
						ps.setString(3, appLog.getContent());
						ps.setString(4, appLog.getCreateTime());
						System.out.println(appLog);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				public int getBatchSize() {
					System.out.println(logList.size());
					return logList.size();
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

}
