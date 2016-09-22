package za.ac.cput.healthcare.domain;

import java.io.Serializable;

/**
 * Author: MI Lagardien
 * Group: 3A
 * Subject: Technical Programming 2 (TPG200s)
 * Lecturer: Dr B. Kabaso
 * Description:
 */
public class Client implements Serializable
{
    private Long id;
    private String name;
    private String lastName;
    private ClientAddress objAdress;
    private Client(ClientBuilder objBuilder)
    {
        this.id=objBuilder.id;
        this.name=objBuilder.name;
        this.lastName=objBuilder.lastName;
        this.objAdress=objBuilder.objAdress;
    }
    public Client(){}

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getName()
    {
        return name;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setObjAdress(ClientAddress objAdress)
    {
        this.objAdress = objAdress;
    }

    public ClientAddress getObjAdress()
    {
        return objAdress;
    }

    public static class ClientBuilder
    {
        private Long id;
        private String name;
        private String lastName;
        private ClientAddress objAdress;

        public ClientBuilder id(Long id)
        {
            this.id=id;
            return this;
        }

        public ClientBuilder name(String name)
        {
            this.name=name;
            return this;
        }
        public ClientBuilder lastName(String lastName)
        {
            this.lastName=lastName;
            return this;
        }
        public ClientBuilder address(ClientAddress objAdress)
        {
            this.objAdress=objAdress;
            return this;
        }
        public ClientBuilder copy(Client objClient)
        {
            this.id= objClient.id;
            this.name= objClient.name;
            this.lastName= objClient.lastName;
            this.objAdress= objClient.objAdress;
            return  this;
        }
        public Client build()
        {
            return new Client(this);
        }


    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
