package eu.eurofel.entities;

import java.io.Serializable;

public class EmailRenewal
    implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 6215145529608501785L;
                    
    private String email;

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }
    
    
}
