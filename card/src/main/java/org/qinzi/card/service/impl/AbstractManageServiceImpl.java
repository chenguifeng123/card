package org.qinzi.card.service.impl;

import org.qinzi.card.exception.GlobalProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.qinzi.card.exception.Error;
/**
 * Created by chenguifeng on 2018/11/27.
 */
public abstract class AbstractManageServiceImpl {

	private Logger logger = LoggerFactory.getLogger(AbstractManageServiceImpl.class);

	public interface ExecuteDaoOperate<T>{
		public int operate(T t) throws Exception;
	}

	/**
	 * 数据库的 DDL 操作
	 * @param t
	 * @param execute
	 * @param error
	 * @param <T>
	 * @return
	 */
	public <T> T daoOperate(T t, ExecuteDaoOperate<T> execute, Error error){
		logger.info("执行操作----------------");
		try {
			int result = execute.operate(t);
			logger.info("操作结果 {}", result);
			return t;
		}catch (Exception e){
			logger.error("操作失败", e);
			throw new GlobalProcessException(error);
		}
	}
}
