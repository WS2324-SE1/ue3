package org.hbrs.se1.ws23.container;

import org.hbrs.se1.ws23.member.Member;
import org.hbrs.se1.ws23.persistence.PersistenceException;
import org.hbrs.se1.ws23.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.List;

public class Container<T extends Member>
{

	private static Container mInstance;

	private PersistenceStrategy<T> mPersistenceStrategy;

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

	private List<T> mMembers = new ArrayList<T>();


	public void addMember(T r) throws ContainerException
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

	private boolean contains(T r)
	{
		Integer ID = r.getID();
		for (T rec : mMembers)
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
		T rec = getMember(id);
		if (rec == null) return "Member nicht enthalten - ERROR";
		else
		{
			mMembers.remove(rec);
			return "Member mit der ID " + id + " konnte gelöscht werden";
		}
	}

	public int size()
	{
		return mMembers.size();
	}


	public List<T> getCurrentList()
	{
		return mMembers;
	}

	private T getMember(Integer id)
	{
		for (T rec : mMembers)
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
		if (mPersistenceStrategy == null)
		{
			throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, "Es ist keine Strategie gesetzt!");
		}

		mPersistenceStrategy.openConnection();

		mPersistenceStrategy.save(mMembers);

		mPersistenceStrategy.closeConnection();
	}


	public void load() throws PersistenceException
	{
		if (mPersistenceStrategy == null)
		{
			throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, "Es ist keine Strategie gesetzt!");
		}

		mPersistenceStrategy.openConnection();

		mMembers = mPersistenceStrategy.load();

		mPersistenceStrategy.closeConnection();
	}

	public PersistenceStrategy<T> getPersistenceStrategy()
	{
		return mPersistenceStrategy;
	}

	public void setPersistenceStrategy(PersistenceStrategy<T> persistence_strategy)
	{
		this.mPersistenceStrategy = persistence_strategy;
	}
}
