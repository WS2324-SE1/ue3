package org.hbrs.se1.ws23.member;

import java.io.Serializable;

public class ConcretMember implements Member
{
	
	private Integer id = null;
	
	public ConcretMember(Integer id ){
		this.id = id;
	}

	@Override
	public Integer getID() { 
		return this.id;
	}
	
	public void setID ( Integer id ) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "MemberKonkret [id=" + id + "]";
	}
}
