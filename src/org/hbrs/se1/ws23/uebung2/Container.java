package org.hbrs.se1.ws23.uebung2;

import org.hbrs.se1.ws23.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws23.uebung3.persistence.PersistenceStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Container
{

	private static Container mInstance;

	private PersistenceStrategy<Member> mPersistenceStrategy;

	private Container()
	{
	}

	static
	{
		mInstance = new Container();
	}

	public static Container getInstance(boolean reset)
	{
		if (reset)
		{
			mInstance = new Container();
		}
		return mInstance;
	}

	public static Container getInstance()
	{
		return mInstance;
	}

	private List<Member> mMembers = new ArrayList<Member>();


	public void addMember(Member r) throws ContainerException
	{

		if (r == null)
		{
			ContainerException ex = new ContainerException();
			throw ex;
		}

		if (contains(r) == true)
		{
			ContainerException ex = new ContainerException(r.getID().toString());
			throw ex;
		}
		mMembers.add(r);

	}

	private boolean contains(Member r)
	{
		Integer ID = r.getID();
		for (Member rec : mMembers)
		{
			// wichtig: Check auf die Values innerhalb der Integer-Objekte!
			if (rec.getID().intValue() == ID.intValue())
			{
				return true;
			}
		}
		return false;

		// liste.contains(r), falls equals-Methode in Member ueberschrieben.
	}

	public String deleteMember(Integer id)
	{
		Member rec = getMember(id);
		if (rec == null) return "Member nicht enthalten - ERROR";
		else
		{
			mMembers.remove(rec);
			return "Member mit der ID " + id + " konnte geloescht werden";
		}
	}

	public int size()
	{
		return mMembers.size();
	}


	public void dump()
	{
		System.out.println("Ausgabe aller Member-Objekte: ");

		for (Member p : mMembers)
		{
			System.out.println(p.toString());
		}
	}

	private Member getMember(Integer id)
	{
		for (Member rec : mMembers)
		{
			if (id == rec.getID().intValue())
			{
				return rec;
			}
		}
		return null;
	}


	public void store() throws PersistenceException
	{
		mPersistenceStrategy.openConnection();

		mPersistenceStrategy.save(mMembers);

		mPersistenceStrategy.closeConnection();
	}


	public void load() throws PersistenceException
	{
		mPersistenceStrategy.openConnection();

		mMembers = mPersistenceStrategy.load();

		mPersistenceStrategy.closeConnection();
	}

	public PersistenceStrategy<Member> getPersistenceStrategy()
	{
		return mPersistenceStrategy;
	}

	public void setPersistenceStrategy(PersistenceStrategy<Member> mPersistenceStrategy)
	{
		this.mPersistenceStrategy = mPersistenceStrategy;
	}
}
