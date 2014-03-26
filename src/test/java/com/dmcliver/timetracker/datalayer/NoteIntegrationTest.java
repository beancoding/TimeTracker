package com.dmcliver.timetracker.datalayer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.timetracker.domain.Note;
import com.dmcliver.timetracker.domain.SysUser;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration("classpath:/servlet-context.xml")
public class NoteIntegrationTest {

	@Autowired
	private SysUserDAO sysUserDAO;
	
	@Autowired
	private NoteDAO noteDAO;
	
	@Test
	public void save(){
		
		SysUser user = new SysUser("testUser1", "testPassword1");
		sysUserDAO.save(user);
		
		Note note = new Note("The quick brown fox jumps over the lazy dog");
		note.setUser(user);
		
		noteDAO.save(note);
	}
}
