/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reguest_parameters_pojos;

/**
 *
 * @author Israa
 */
public class GetUserOffersParam {
    private int userId;

    public GetUserOffersParam() {
    }

    public GetUserOffersParam(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
}
