package org.qinzi.card.service.impl;

import org.qinzi.card.dao.MemberCardMapper;
import org.qinzi.card.exception.Error;
import org.qinzi.card.exception.GlobalProcessException;
import org.qinzi.card.po.MemberCard;
import org.qinzi.card.po.MemberCardExample;
import org.qinzi.card.service.MemberCardServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by chenguifeng on 2020/5/16.
 */
@Service
public class MemberCardServiceImpl  extends AbstractManageServiceImpl implements MemberCardServiceI{

	@Autowired
	MemberCardMapper memberCardMapper;


	private static enum MemberCardServiceError implements Error {
		E_M0000001("M0000001","用户已存在"),
		E_M0000002("M0000002","新增失败"),
		E_M0000003("M0000003","修改失败"),
		E_M0000004("M0000004","删除失败"),
		E_M0000005("M0000005","记录不存在"),;

		String code;

		String msg;

		MemberCardServiceError(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		@Override
		public String code() {
			return code;
		}

		@Override
		public String msg() {
			return msg;
		}
	}

	public List<MemberCard> listMemberCards() {
		MemberCardExample memberCardExample = new MemberCardExample();
		return memberCardMapper.selectByExample(memberCardExample);
	}

	public MemberCard addMemberCard(MemberCard memberCard) {
		MemberCardExample memberCardExample = new MemberCardExample();
		MemberCardExample.Criteria criteria = memberCardExample.createCriteria();

		criteria.andPhoneEqualTo(memberCard.getPhone());
		criteria.andCardTypeEqualTo(memberCard.getCardType());
		List<MemberCard> list = memberCardMapper.selectByExample(memberCardExample);
		if(list != null && list.size() > 0)
			throw new GlobalProcessException(MemberCardServiceError.E_M0000001);

		// 执行插入操作
		return daoOperate(memberCard, (MemberCard o) ->{
			return memberCardMapper.insert(o);
		}, MemberCardServiceError.E_M0000002);
	}

	public int deleteMemberCard(int id) {
		return daoOperate(id, (Integer o) ->{
			return memberCardMapper.deleteByPrimaryKey(o);
		}, MemberCardServiceError.E_M0000004);
	}

	public MemberCard updateMemberCard(MemberCard memberCard) {
		return daoOperate(memberCard, (MemberCard o) ->{
			return memberCardMapper.updateByPrimaryKey(o);
		}, MemberCardServiceError.E_M0000003);
	}
}
