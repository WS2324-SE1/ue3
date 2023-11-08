package org.hbrs.se1.ws23.persistence;

import java.io.*;
import java.util.List;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    private FileOutputStream mFileOutputStream = null;
    private FileInputStream mFileInputStream = null;
    private ObjectOutputStream mObjectOutputStream = null;
    private ObjectInputStream mObjectInputStream = null;

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    /**
     * Method for opening the connection to a stream (here: Input- and Output-Stream)
     * In case of having problems while opening the streams, leave the code in methods load
     * and save.
     */
    public void openConnection() throws PersistenceException {
        try{
            mFileOutputStream = new FileOutputStream(location);
            mFileInputStream = new FileInputStream(location);
        }
        catch (FileNotFoundException e)
        {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "File Stream could not be opened!");
        }

        try {
            mObjectOutputStream = new ObjectOutputStream(mFileOutputStream);
            mObjectInputStream = new ObjectInputStream(mFileInputStream);
        }
        catch (IOException e)
        {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "Object Stream could not be opened!");
        }
    }

    @Override
    /**
     * Method for closing the connections to a stream
     */
    public void closeConnection() throws PersistenceException {
        try {
            mObjectOutputStream.close();
            mObjectInputStream.close();
        }
        catch (IOException e)
        {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "Object Stream could not be closed!");
        }

        try {
            mFileOutputStream.close();
            mFileInputStream.close();
        }
        catch (IOException e)
        {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "File Stream could not be closed!");
        }
    }

    @Override
    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     */
    public void save(List<E> member) throws PersistenceException
    {
        try {
            mObjectOutputStream.writeObject(member);
        }
        catch (IOException e)
        {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "Object Stream could not be written!");
        }
    }

    @Override
    /**
     * Method for loading a list of Member-objects from a disk (HDD)
     * Some coding examples come for free :-)
     * Take also a look at the import statements above ;-!
     */
    public List<E> load() throws PersistenceException  {

        try {
            return (List<E>) mObjectInputStream.readObject();
        }
        catch (IOException e)
        {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "Object Stream could not be read!");
        }
        catch (ClassNotFoundException e)
        {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "Object Stream could not be read!");
        }
        catch (ClassCastException e)
        {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "Object Stream could not be read!");
        }
    }
}
