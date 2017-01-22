package com.dzdp.rs.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dzdp.rs.constants.Constants;
@Service
@Transactional
public class IdGenUtils {
	public static final Long subsysid = Constants.getCurrentSysID();   
	
	public static final int pk_Code_Length = Constants.SYS_ID_LENGTH;
    
    public static Long getId(String val){
    	return new Long(subsysid *pk_Code_Length+Integer.parseInt(val));
    }
    public static void main(String[] args) {
		IdGenUtils idGen = new IdGenUtils();
		System.out.println(idGen.getId("54"));
	}
}
