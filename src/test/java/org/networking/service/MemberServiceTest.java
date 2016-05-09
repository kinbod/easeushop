/**
 * 
 */
package org.networking.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.networking.Application;
import org.networking.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author carlquan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Test
	public void findsFirstPageOfCities() {

		List<Member> cities = this.memberService.findAll();
		assertEquals(cities.size(),1);
	}
}
