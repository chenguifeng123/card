package org.qinzi.card.controller;

import org.qinzi.card.po.MemberCard;
import org.qinzi.card.service.MemberCardServiceI;
import org.qinzi.card.web.QueryInfo;
import org.qinzi.card.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chenguifeng on 2020/5/16.
 */
@RestController
public class MemberCardController {

	@Autowired
	MemberCardServiceI memberCardServiceI;

	QueryInfo getQueryByPage(int pageNo, int pageSize){
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.setPageNo(pageNo);
		queryInfo.setPageSize(pageSize);
		return queryInfo;
	}

	@GetMapping("/listMemberCards/{pageNo}-{pageSize}")
	public Result listMemberCards(@PathVariable("pageNo") int pageNo,
								  @PathVariable("pageSize") int pageSize){
		return Result.okWithPage(()-> memberCardServiceI.listMemberCards(),
				getQueryByPage(pageNo, pageSize));
	}

	@PutMapping("/memberCard")
	public Result addMemberCard(@RequestBody MemberCard memberCard){
		return Result.ok(memberCardServiceI.addMemberCard(memberCard));
	}

	@PostMapping("/memberCard")
	public Result updateMemberCard(@RequestBody MemberCard memberCard){
		return Result.ok(memberCardServiceI.updateMemberCard(memberCard));
	}

	@DeleteMapping("/memberCard/{id}")
	public  Result deleteMemberCard(@PathVariable("id") int id){
		return Result.ok(memberCardServiceI.deleteMemberCard(id));
	}
}
