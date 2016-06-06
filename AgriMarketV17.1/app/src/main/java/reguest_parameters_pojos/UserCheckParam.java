/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reguest_parameters_pojos;

/**
 *
 * @author islam
 */
public class UserCheckParam {

    private String email;
    private Integer registrationChannel;


    public UserCheckParam(String email, Integer registrationChannel) {
        this.email = email;
        this.registrationChannel = registrationChannel;
    }
    

    public Integer getRegistrationChannel() {
        return registrationChannel;
    }

    public void setRegistrationChannel(Integer registrationChannel) {
        this.registrationChannel = registrationChannel;
    }

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
