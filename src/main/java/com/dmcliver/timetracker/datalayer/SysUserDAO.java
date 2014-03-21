package com.dmcliver.timetracker.datalayer;

import java.util.List;

import com.dmcliver.timetracker.domain.SysUser;

public interface SysUserDAO {

	public abstract List<SysUser> findAll();

}