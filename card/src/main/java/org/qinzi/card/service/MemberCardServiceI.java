package org.qinzi.card.service;

import org.qinzi.card.po.MemberCard;

import java.util.List;

/**
 * Created by chenguifeng on 2020/5/16.
 */
public interface MemberCardServiceI {
	List<MemberCard> listMemberCards();
	MemberCard addMemberCard(MemberCard memberCard);
	int deleteMemberCard(int id);
	MemberCard updateMemberCard(MemberCard memberCard);
}
