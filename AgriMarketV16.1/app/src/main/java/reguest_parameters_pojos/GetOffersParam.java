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
public class GetOffersParam {
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public GetOffersParam() {
    }

    public GetOffersParam(int productId) {
        this.productId = productId;
    }
    
    
}
