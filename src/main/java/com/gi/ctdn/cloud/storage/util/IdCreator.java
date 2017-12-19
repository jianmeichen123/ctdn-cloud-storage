package com.gi.ctdn.cloud.storage.util;

/**
 * @author kaihu
 */
public interface IdCreator {
	public Long nextId(String sKey) throws Exception;
}
